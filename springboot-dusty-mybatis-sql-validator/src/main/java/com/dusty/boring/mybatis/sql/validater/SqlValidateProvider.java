/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 23:35
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.ZERO;
import static com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter.blackSqlList;
import static com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter.dbiDataList;
import static com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter.whiteSqlList;

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
public abstract class SqlValidateProvider {
    
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
    
    public SqlValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        this.dbType = dbType;
        this.sqlValidatorProperties = sqlValidatorProperties;
    }
    
    /**
     * <pre>
     *     检查合法性
     *
     * @param   sql 带校验的sql语句
     * @return  校验结果
     * </pre>
     */
    public boolean validateSql(String sql) {
        
        final SqlValidateResult result = validateInternal(sql);
        return Objects.isNull(result) || ZERO.equals(result.getViolations().size());
    }
    
    public SqlValidateResult validateInternal(String sql) {
        
        
        return new SqlValidateResult(sql);
    }
    
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
