/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 22:15
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.intercept;

import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.annotation.IgnoreSqlChecker;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.cache.LocalLRUCache;
import com.dusty.boring.mybatis.sql.common.context.SpringContextHolder;
import com.dusty.boring.mybatis.sql.common.exception.IllegalSqlValidateException;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.dusty.boring.mybatis.sql.common.utils.EncryptUtils;
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import com.dusty.boring.mybatis.sql.validater.provider.AbstractSqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.provider.OracleValidateProvider;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.*;

/**
 * <pre>
 *
 *       <糟糕SQL检查>
 *
 *           - 监控sql执行效率
 *           - 校验sql规范情况
 *             目前主要为where条件空拦截/查询未使用索引拦截/Or拦截/‘<>’拦截
 *
 *           - SQL解析资料:<a href='https://github.com/alibaba/druid'>Druid</a>
 *           - SQL解析资料:<a href='https://github.com/JSQLParser/JSqlParser'>JSqlParser</a>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 22:15
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月13日 22:15
 * </pre>
 */
/**
 * 拦截prepare方法
 * {@link org.apache.ibatis.executor.statement.RoutingStatementHandler#prepare(Connection, Integer)}
 *
 * P:
 *  - Connection: connection
 *  - Integer:transactionTimeout
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class BadSqlValidateIntercepter implements Interceptor {
    
    
    @MetaData(value = "检查白名单", note = "已知可忽略检查的sql")
    public static LocalLRUCache<String, String> whiteSqlList;
    
    @MetaData(value = "索引信息缓存")
    public static LocalLRUCache<String, List<DbiData>> dbiDataList;
    
    @MetaData(value = "索引信息", note = "用于替换dibDataList")
    public static LocalLRUCache<String, Map<String, String>> dbiDataCache;
    
    @MetaData(value = "检查黑名单", note = "已知未通过检查的sql")
    public static LocalLRUCache<String, SqlValidateResult> blackSqlList;
    
    @MetaData(value = "注解忽略检查的方法")
    public static final Set<String> annotatedIgnoreMthds;
    
    static {
        
        blackSqlList = new LocalLRUCache<>(256);
        dbiDataList  = new LocalLRUCache<>(1024);
        whiteSqlList = new LocalLRUCache<>(1024);
        annotatedIgnoreMthds = Sets.newConcurrentHashSet();
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    
        String    toExecSql                   = null;
        boolean   checked                     = false;
        Stopwatch stopwatch                   = Stopwatch.createStarted();
        SqlValidatorProperties sqlValidatorProperties = SpringContextHolder.getBean(SqlValidatorProperties.class);
        
        try {
    
            //当前环境忽略检查
            if (ignoreValidateByEnvs(sqlValidatorProperties)) {
                return invocation.proceed();
            }
    
            //当前系统配置忽略检查
            final SqlValidatorProperties.MySqlValidateItems items = sqlValidatorProperties.getMySqlValidItems();
            if (!items.anyItemsTrue()) {
                return invocation.proceed();
            }
            
            //准备sql校验资源
            Connection conn                 = (Connection) invocation.getArgs()[0];
            final DatabaseMetaData metaData = conn.getMetaData();
            final String dbTypeName         = metaData.getDatabaseProductName();
            MetaObject metaObject           = SystemMetaObject.forObject(getRealTarget(invocation.getTarget()));
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(DELEGATE_MAPPED_STMT);
    
            //标注IgnoreSqlChecker
            if (ignoreValidateByAnnotated(mappedStatement)) {
                return invocation.proceed();
            }
    
            BoundSql boundSql               = (BoundSql) metaObject.getValue(DELEGATE_BOUND_SQL);
            toExecSql                       = boundSql.getSql();
    
            //定义sql操作镜像副本
            String sqlImage = StringUtils.replaceAll(toExecSql, "\\t|\\r|\\n", "");
            String encryptedSql = EncryptUtils.base64(EncryptUtils.md5(sqlImage));
            
            //检查sql白名单（包括已知通过校验的sql及已知标记‘IgnoreSqlChecker’忽略的sql）
            if (enabledEnvsWhiteList(sqlValidatorProperties) && whiteSqlList.containsKey(encryptedSql)) {
               return invocation.proceed();
            }
            
            //检查已知黑名单
            if (enabledEnvsBlackList(sqlValidatorProperties)) {
                SqlValidateResult result = blackSqlList.get(encryptedSql);
                if (Objects.nonNull(result) && result.getViolations().size() > 0) {
                    final List<SqlValidateResult.Violation> violations = result.getViolations();
                    final SqlValidateResult.Violation       violation  = violations.get(0);
                    throw new IllegalStateException(String.format("校验未通过，错误码:%s,错误信息:%s", violation.getErrorCode(), violation.getMessage()));
                }
            }
            
            //忽略检查INSERT语句
            if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
                return invocation.proceed();
            }
    
            AbstractSqlValidateProvider sqlValidateProvider = getSqlValidateProvider(dbTypeName);
            final SqlValidateResult validateResult = sqlValidateProvider.validateSqlWithResult(toExecSql, encryptedSql, conn);
            if (Objects.nonNull(validateResult) && validateResult.getViolations().size() > 0) {
                //验证不通过
                throw IllegalSqlValidateException
                        .builder()
                            .code(SqlErrorCodeEnum.SQL9000.name())
                            .message(SqlErrorCodeEnum.SQL9000.getLabel())
                            .sqlInfo(validateResult.getSql())
                            .violations(validateResult.getViolations())
                        .build();
            }
        } finally {
            
            stopwatch.stop();
            if (SpringContextHolder.isDemoMode()) {//开发或测试模式
                final long costTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                if (sqlValidatorProperties.getWarnOverMills() <= costTime) {
                    log.warn("\n-\tSQL执行完成:" +
                             "\n-\t是否检查:{}" +
                             "\n-\t总计耗时:{}" +
                             "\n-\tSql内容:{}", checked, costTime,  toExecSql);
                }
            }
        }
        
        return invocation.proceed();
    }
    
    
    @Override
    public Object plugin(Object target) {
        
        //拦截StatementHandler, 返回代理对象，减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
    
        return target;
    }
    
    @Override
    public void setProperties(Properties properties) {
    
        //~~ 在Configuration初始化当前的Interceptor时就会执行 ~~
        log.info("\n-\tsetProperties：\n-\tProperties：{}个", properties.stringPropertyNames().size());
    }
    
    /**
     * <pre>
     *     剥离多层代理，获取真正的处理对象
     *
     * @param target      可能经过代理的处理对象
     * @param <T>         返回值类型
     * @return realTarget 真正的处理对象
     * </pre>
     */
    private <T> T getRealTarget(Object target) {
        
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return getRealTarget(metaObject);
        }
        
        return (T) target;
    }
    
    /**
     * <pre>
     *     当前环境是否忽略sql质量检查的环境
     *
     * @param  properties 检查配置
     * @return bool
     *           - true  : 是（忽略检查）
     *           - false : 否（不忽略检查）
     * </pre>
     */
    private static boolean ignoreValidateByEnvs(SqlValidatorProperties properties) {
        return properties.getEnvProfiles().getIgnoreCheckEnvs().contains(SpringContextHolder.getActiveProfile());
    }
    
    /**
     * <pre>
     *     是否配置忽略检查
     *
     * @param  stmt MappedStatement
     * @return bool
     * </pre>
     */
    private static boolean ignoreValidateByAnnotated(MappedStatement stmt) throws ClassNotFoundException {
        
        final String id = stmt.getId();
        
        if (ANNOTATED_IGNORE_CHECK_METHODS.contains(id)) {
            return true;
        }
        
        if (NON_IGNORE_CHECK_METHODS.contains(id)) {
            return false;
        }
        
        //已知不忽略检查
        final String stmtId = stmt.getId();
        final String classNm  = StringUtils.substring(stmtId, 0, stmtId.lastIndexOf("."));
        final String methodNm = StringUtils.substring(stmtId, stmtId.lastIndexOf(".") + 1, stmtId.length());
        final Method[] methods = Class.forName(classNm).getMethods();
        
        for (Method m : methods) {
            
            if (methodNm.equals(m.getName())) {
                
                if (m.isAnnotationPresent(IgnoreSqlChecker.class)) {
                    //忽略SQL检查,代理类，无法获取方法注解
                    ANNOTATED_IGNORE_CHECK_METHODS.add(stmtId);
                } else {
                    NON_IGNORE_CHECK_METHODS.add(stmtId);
                }
                
                break;
            }
        }
        
        return ANNOTATED_IGNORE_CHECK_METHODS.contains(stmt.getId());
    }
    
    /**
     * <pre>
     *     当前环境是否启用白名单
     *
     * @param  properties 检查配置
     * @return bool
     *           - true  : 是（启用）
     *           - false : 否（未启用）
     * </pre>
     */
    private static boolean enabledEnvsWhiteList(SqlValidatorProperties properties) {
        return properties.getEnvProfiles().getEnableWhiteListCacheEnvs().contains(SpringContextHolder.getActiveProfile());
    }
    
    /**
     * <pre>
     *     当前环境是否启用黑名单
     *
     * @param  properties 检查配置
     * @return bool
     *           - true  : 是（启用）
     *           - false : 否（未启用）
     * </pre>
     */
    private static boolean enabledEnvsBlackList(SqlValidatorProperties properties) {
        return properties.getEnvProfiles().getEnableBlackListCacheEnvs().contains(SpringContextHolder.getActiveProfile());
    }
    
    /**
     * <pre>
     *     获取sql检查器
     *
     * @param  dbTypeName 数据库类型名称（如:MySql）
     * @return SqlValidateProvider实例
     * </pre>
     */
    private static AbstractSqlValidateProvider getSqlValidateProvider(String dbTypeName) {
    
        AbstractSqlValidateProvider sqlValidateProvider = null;
        final DbTypeEnum dbTypeEnum = DbTypeEnum.valueOf(dbTypeName);
        if (DbTypeEnum.MySql.equals(dbTypeEnum)) {
            sqlValidateProvider = SpringContextHolder.getBean(MySqlValidateProvider.class);
        } else if (DbTypeEnum.Oracle.equals(dbTypeEnum)) {
            sqlValidateProvider = SpringContextHolder.getBean(OracleValidateProvider.class);
        }
    
        Assert.notNull(sqlValidateProvider, "SqlValidateProvide不能为空！");
        
        return sqlValidateProvider;
    }
    
}
