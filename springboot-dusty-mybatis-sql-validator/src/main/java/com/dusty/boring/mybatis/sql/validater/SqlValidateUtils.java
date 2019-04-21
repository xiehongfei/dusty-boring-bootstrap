/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 21:51
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlLockTableStatement;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties.MySqlValidateItems;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.CACHED_VAL;
import static com.dusty.boring.mybatis.sql.validater.SqlValidateResult.TableInfo;
/**
 * <pre>
 *
 *       <Dbi ValidateUtils>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 21:51
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月17日 21:51
 * </pre>
 */
public class SqlValidateUtils {
    
    
    /**
     * <pre>
     *
     *     preVisitCheck
     *     - 因项目需求，目前仅提供MySQL preVisitCheck方法
     *
     * @param visitor sqlVisitor
     * @param x       sqlObject
     * </pre>
     */
    public static void preVisitCheck(SqlValidateVisitor visitor, SQLObject x) {
    
        final SqlValidatorProperties properties  = visitor.getSqlValidatorProperties();
    
        MySqlValidateItems validateItems =  properties.getMySqlValidItems();
        
        //非SQL语法，直接返回
        if (!(x instanceof SQLStatement)) {
            return;
        }
    
        boolean valid        = true;
        String errorCode     = null;
        String denyMessage   = null;
        
        if (x instanceof SQLDropStatement) {
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            valid = validateItems.isEnableDdlDrop();
            denyMessage = "禁止使用DDL语法: drop";
        } else if (x instanceof SQLTruncateStatement) {
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            denyMessage = "禁止使用truncate命令";
        } else if (x instanceof SQLAlterStatement) {
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            denyMessage = "禁止使用alter命令";
        } else if (x instanceof SQLCommentStatement) {
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            valid = validateItems.isEnableDdlComment();
            denyMessage = "禁止使用DDL语法: comment";
        } else if (x instanceof SQLExplainStatement) {
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            valid = validateItems.isEnableExplain();
            denyMessage = "禁止使用explain命令";
        } else if (x instanceof MySqlLockTableStatement) {
            
            errorCode = SqlErrorCodeEnum.SQL9000.name();
            valid = validateItems.isEnableLockTable();
            denyMessage = "禁止使用lock table命令";
        }
        
        if (!valid) {
            visitor.addViolation(new SqlValidateResult.Violation(errorCode, denyMessage));
        }
    }
    
    /**
     * <pre>
     *     根据表信息获取索引集
     *
     * @param conn          conn
     * @param table         table信息
     * @return              List<DbiData> 索引信息集
     * @throws Throwable
     * </pre>
     */
    public static List<MyBatisConstPool.DbiData> getDbiDatasByTableInfo(Connection conn, TableInfo table)  throws Throwable {
        
        String tbName = table.getOwner();
        String dbName = table.getTableName();
    
        List<MyBatisConstPool.DbiData> dbiDatas = BadSqlValidateIntercepter.dbiDataList.get(tbName);
        
        if (Objects.isNull(dbiDatas)) {
            
            dbiDatas = Lists.newArrayList();
            //unique:是否只获取unique, approximate:是否精确拉取
            final DatabaseMetaData metaData = conn.getMetaData();
            final ResultSet indexInfo       = metaData.getIndexInfo(dbName, dbName, tbName, false, false);
            MyBatisConstPool.DbiData dbiData;
            
            while (indexInfo.next()) {
                
                if ("1".equals(indexInfo.getString("ORDINAL_POSITION"))) {
                    dbiData = new MyBatisConstPool.DbiData();
                    dbiData.setTableCat(indexInfo.getString("TABLE_CAT"));
                    dbiData.setTableSchem(indexInfo.getString("TABLE_SCHEM"));
                    dbiData.setTableName(indexInfo.getString("TABLE_NAME"));
                    dbiData.setUnique(!indexInfo.getBoolean("NON_UNIQUE"));
                    dbiData.setIndexQualifier(indexInfo.getString("INDEX_QUALIFIER"));
                    dbiData.setIndexName(indexInfo.getString("INDEX_NAME"));
                    dbiData.setIndexType(indexInfo.getInt("TYPE"));
                    dbiData.setSeqInIndex(indexInfo.getString("ORDINAL_POSITION"));
                    dbiData.setColumnName(indexInfo.getString("COLUMN_NAME"));
                    dbiData.setAscOrDesc(indexInfo.getString("ASC_OR_DESC"));
                    dbiData.setCardinality(indexInfo.getString("CARDINALITY"));
                    dbiData.setPages(indexInfo.getString("PAGES"));
                    dbiDatas.add(dbiData);
                }
            }
            
            //放入本地LRU缓存
            if (CollectionUtils.isNotEmpty(dbiDatas))
                BadSqlValidateIntercepter.dbiDataList.put(tbName, dbiDatas);
        }
        
        return dbiDatas;
    }
    
    /**
     * <pre>
     *     根据表信息获取索引集
     *
     * @param conn          conn
     * @param table         table信息
     * @return              List<DbiData> 索引信息集
     * @throws Throwable
     * </pre>
     */
    public static Map<String, String> getDbiDatasFromCache(Connection conn, TableInfo table)  throws Throwable {
        
        String tbName = table.getOwner();
        String dbName = table.getTableName();
        
        Map<String, String> dbiMap              = BadSqlValidateIntercepter.dbiDataCache.get(tbName);
        
        if (MapUtils.isEmpty(dbiMap)) {
            
            dbiMap = Maps.newHashMap();
            //unique:是否只获取unique, approximate:是否精确拉取
            final DatabaseMetaData metaData = conn.getMetaData();
            final ResultSet indexInfo       = metaData.getIndexInfo(dbName, dbName, tbName, false, false);
            
            while (indexInfo.next()) {
                if ("1".equals(indexInfo.getString("ORDINAL_POSITION"))) {
                    dbiMap.put(indexInfo.getString("COLUMN_NAME"), CACHED_VAL);
                }
            }
            
            //放入本地LRU缓存
            if (MapUtils.isNotEmpty(dbiMap)) {
                BadSqlValidateIntercepter.dbiDataCache.put(tbName, dbiMap);
            }
        }
        
        return dbiMap;
    }
    
    
}
