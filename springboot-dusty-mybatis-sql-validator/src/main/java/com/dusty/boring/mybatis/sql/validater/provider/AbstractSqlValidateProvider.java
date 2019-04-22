/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 23:35
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.provider;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.cache.LocalLRUCache;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.ZERO;
import static com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter.*;
import static com.dusty.boring.mybatis.sql.validater.SqlValidateResult.*;

/**
 * <pre>
 *
 *       <SQLValidateProvider>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 23:35
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月13日 23:35
 * </pre>
 */
public abstract class AbstractSqlValidateProvider {
    
    @MetaData(value = "DB 类型")
    @Getter
    @Setter
    private MyBatisConstPool.DbTypeEnum dbType;
    
    @MetaData(value = "相关配置")
    @Getter
    @Setter
    private SqlValidatorProperties sqlValidatorProperties;
    
    @MetaData(value = "读写锁")
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public AbstractSqlValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        this.dbType = dbType;
        this.sqlValidatorProperties = sqlValidatorProperties;
    }
    
    public abstract SQLStatementParser createParser(String sql);
    
    public abstract SqlValidateVisitor createValidateVisitor();
    
    
    /**
     * <pre>
     *     检查合法性
     *
     * @param   sql      带校验的sql语句
     * @param   cacheKey 缓存key（sql语句清洗后加密内容）
     * @param   conn     数据库链接
     * @return  校验结果
     * </pre>
     */
    public boolean validateSql(String sql, String cacheKey, Connection conn) throws Throwable {
        
        final SqlValidateResult result = validateSqlWithResult(sql, cacheKey, conn);
        return Objects.isNull(result) || ZERO.equals(result.getViolations().size());
    }
    
    
    /**
     * <pre>
     *     执行sql检查，并返回检查结果
     *
     * @param sql       待检查的sql
     * @param cacheKey  本地缓存key
     * @return   sql质量检查结果
     * </pre>
     */
    public SqlValidateResult validateSqlWithResult(String sql, String cacheKey, Connection conn) throws Throwable {
    
        final MySqlStatementParser mySqlStatementParser =
                                  new MySqlStatementParser(sql, SQLParserFeature.EnableSQLBinaryOpExprGroup, SQLParserFeature.StrictForWall);
        
        final List<SQLStatement> sqlStatements = mySqlStatementParser.parseStatementList();
        Assert.isTrue(Objects.nonNull(sqlStatements) && sqlStatements.size() == 1, "sqlStatements size != 1");
        
        final SQLStatement sqlStatement = sqlStatements.get(0);
        final SqlValidateVisitor validateVisitor = createValidateVisitor();
        //执行pre检查(主要检查语法类型是否合法。如：是否ddl语句等校验)
        SqlValidateUtils.preVisitCheck(validateVisitor, sqlStatement);
        List<Violation> violations = validateVisitor.getViolations();
        
        if (CollectionUtils.isEmpty(violations)) {
            //解析AST，并获取执行结果
            sqlStatement.accept(validateVisitor);
            violations = validateVisitor.getViolations();
        }
        
        if (CollectionUtils.isNotEmpty(violations)) {
            
            final SqlValidateResult validateResult = new SqlValidateResult(sql, violations);
            addBlackSql(cacheKey, validateResult);
            return validateResult;
        }
        
        
        
    
        //~~ 检查是否使用索引 ~~
        final Map<String, TableInfo> tables      = validateVisitor.getTables();
        final Map<String, TableR2Column> columns = validateVisitor.getColumns();
        
        if (getSqlValidatorProperties().getMySqlValidItems().isMustUseIndexCheck()
                && MapUtils.isNotEmpty(tables)
                && MapUtils.isNotEmpty(columns)
                && !validateUseIndex(conn, tables.entrySet().iterator(), columns)) {
    
            violations = Lists.newArrayList();
            violations.add(new Violation(SqlErrorCodeEnum.SQL9006, sql));
        
            final SqlValidateResult validateResult = new SqlValidateResult(sql, violations);
            return addBlackSql(cacheKey, validateResult);
        }
    
        return new SqlValidateResult(sql);
    }
    
    
    /**
     * <pre>
     *     检查是否使用了索引查询
     *
     * @param conn
     * @param tableInfoEntry
     * @param columns
     * @return
     * @throws Throwable
     * </pre>
     */
    public static boolean validateUseIndex(final Connection conn, final Iterator<Map.Entry<String, TableInfo>> tableInfoEntry, final Map<String, TableR2Column> columns) throws Throwable {
    
        boolean useIndex = false;
        
        while (tableInfoEntry.hasNext()) {
            
            final Map.Entry<String, TableInfo> tEntry = tableInfoEntry.next();
            final TableInfo tableInfo = tEntry.getValue();
            //获取索引信息
            final Map<String, String> dbiDataCache = SqlValidateUtils.getDbiDatasFromCache(conn, tableInfo);
            
            if (MapUtils.isNotEmpty(columns)) {
                for (Map.Entry<String, TableR2Column> cEntry : columns.entrySet()) {
                    final TableR2Column column = cEntry.getValue();
                    if (Objects.nonNull(dbiDataCache.get(column.getColumnName()))) {
                        useIndex = true;
                        break;
                    }
                }
            }
        }
        
        return useIndex;
    }
    
    public static void main(String[] args) {
    
    }
    
//    /**
//     * <pre>
//     *     强制不可使用的命令
//     *
//     * @param   cacheKey     缓存Key
//     * @param   sql          sql语句
//     * @param   sqlStatement 待检查的stmt
//     * @return  bool
//     *            - true  : 禁止执行
//     *            - false : 允许执行
//     * </pre>
//     */
//    private SqlValidateResult forceForbidStatement(String cacheKey, String sql, SQLStatement sqlStatement) {
//
//        Assert.notNull(sqlStatement, "SQLStatement不能为空!");
//
//        boolean forbidSql =
//                   sqlStatement instanceof SQLCommentStatement
//                || sqlStatement instanceof SQLTruncateStatement //TRUNCATE: TRUNCATE TABLE用于删除表中的所有行，而不记录单个行删除操作
//                || sqlStatement instanceof SQLDropStatement;
//
//        SqlValidateResult result = null;
//
//        //是禁止执行的Sql，则加黑
//        if (forbidSql) {
//            SqlValidateResult.Violation violation
//                    = new SqlValidateResult.Violation(SQL9000.name(), SQL9000.getLabel());
//
//            result  = new SqlValidateResult(sql, Collections.singletonList(violation));
//            addBlackSql(cacheKey, result);
//        }
//
//        return result;
//    }
    
    public Set<String> getWhiteSqlList() {
        
        Set<String> sqlSet = Sets.newHashSet();
        lock.readLock().lock();
        try {
            
            if (Objects.nonNull(whiteSqlList))
                sqlSet.addAll(whiteSqlList.keySet());
            
        } finally {
            lock.readLock().unlock();
        }
        
        return Collections.unmodifiableSet(sqlSet);
    }
    
    public Set<String> getBlackSqlList() {
        
        Set<String> sqlSet = Sets.newHashSet();
        lock.readLock().lock();
        try {
            
            if (Objects.nonNull(blackSqlList))
                sqlSet.addAll(blackSqlList.keySet());
            
        } finally {
            lock.readLock().unlock();
        }
        
        return Collections.unmodifiableSet(sqlSet);
    }
    
    /**
     * <pre>
     *     增加黑名单Sql
     *
     * @param cacheKey          缓存Key
     * @param sqlValidateResult 检查结果（加黑原因）
     * </pre>
     */
    public SqlValidateResult addBlackSql(String cacheKey, SqlValidateResult sqlValidateResult) {
    
        //不启用黑名单缓存，则返回
        if (!sqlValidatorProperties.getEnvProfiles().enableBlackListCache()) {
            return sqlValidateResult;
        }
        
        //锁定，拉黑
        lock.writeLock().lock();
        try {
            
            if (blackSqlList == null) {
                blackSqlList = new LocalLRUCache<>(256);
            }
            
            blackSqlList.put(cacheKey, sqlValidateResult);
        } finally {
            lock.writeLock().unlock();
        }
        
        return sqlValidateResult;
    }
    
    public void clearCache() {
        
        lock.writeLock().lock();
        
        try {
            
            if (Objects.nonNull(whiteSqlList))
                whiteSqlList = null;
            
            if (Objects.nonNull(blackSqlList))
                blackSqlList = null;
            
            if (Objects.nonNull(dbiDataList))
                dbiDataList = null;
            
        } finally {
            lock.writeLock().unlock();
        }
    }
}
