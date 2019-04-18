/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 23:35
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.provider;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.cache.LocalLRUCache;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.ZERO;
import static com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter.*;

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
     * @return  校验结果
     * </pre>
     */
    public boolean validateSql(String sql, String cacheKey) {
        
        final SqlValidateResult result = validateSqlWithResult(sql, cacheKey);
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
    public SqlValidateResult validateSqlWithResult(String sql, String cacheKey) {
    
        final MySqlStatementParser mySqlStatementParser =
                                  new MySqlStatementParser(sql, SQLParserFeature.EnableSQLBinaryOpExprGroup, SQLParserFeature.StrictForWall);
        
        final List<SQLStatement> sqlStatements = mySqlStatementParser.parseStatementList();
        
        Assert.isTrue(Objects.nonNull(sqlStatements) && sqlStatements.size() == 1, "sqlStatements size != 1");
        
        SQLExpr where = null;
        final SQLStatement sqlStatement = sqlStatements.get(0);
        final SqlValidateVisitor validateVisitor = createValidateVisitor();
        //执行pre检查(主要检查语法类型是否合法。如：是否ddl语句等校验)
        SqlValidateUtils.preVisitCheck(validateVisitor, sqlStatement);
        
        List<SqlValidateResult.Violation> violations = validateVisitor.getViolations();
        
        if (CollectionUtils.isNotEmpty(violations)) {
            
            final SqlValidateResult validateResult = new SqlValidateResult(sql, violations);
            
            addBlackSql(cacheKey, validateResult);
            return validateResult;
            
        } else if (sqlStatement instanceof SQLSelectStatement) {
            //查询
            final SQLSelectQuery sqlQuery = ((SQLSelectStatement) sqlStatement).getSelect().getQuery();
            if (sqlQuery instanceof MySqlSelectQueryBlock) {
                where = ((MySqlSelectQueryBlock) sqlQuery).getWhere();
                final SQLLimit limit = ((MySqlSelectQueryBlock) sqlQuery).getLimit();
            }
        } else if (sqlStatement instanceof SQLUpdateStatement) {
            //更新
            where = ((SQLUpdateStatement) sqlStatement).getWhere();
        } else if (sqlStatement instanceof SQLDeleteStatement) {
            //删除
            where = ((SQLDeleteStatement) sqlStatement).getWhere();
        }
    
        return new SqlValidateResult(sql);
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
    public void addBlackSql(String cacheKey, SqlValidateResult sqlValidateResult) {
    
        //不启用黑名单缓存，则返回
        if (!sqlValidatorProperties.getEnvProfiles().enableBlackListCache()) {
            return;
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
