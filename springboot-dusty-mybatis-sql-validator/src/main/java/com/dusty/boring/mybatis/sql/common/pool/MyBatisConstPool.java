package com.dusty.boring.mybatis.sql.common.pool;

import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *
 *       <Mybatis常量池>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年03月26日 17:34
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年03月26日 17:34
 * </pre>
 */
public interface MyBatisConstPool {
    
    Integer ZERO = Integer.valueOf("0");
    
    Integer ONE = Integer.valueOf("1");
    
    String MD5 = "MD5";
    
    String DELEGATE_BOUND_SQL      = "delegate.boundSql";
    
    String DELEGATE_MAPPED_STMT    = "delegate.mappedStatement";
    
    //String DELEGATE_EXECUTOR       = "delegate.executor";
    
    String CACHED_VAL = "CACHED_VAL";
    
    String BAD_SQL_ERR_CODE = "DB9999";
    
    String SQL_COUNT_EXPRESSION = "count";
    
    
    @MetaData(value = "未配置忽略sql检查的方法")
    Set<String> NON_IGNORE_CHECK_METHODS = Sets.newConcurrentHashSet();
    
    @MetaData(value = "配置忽略sql检查的方法")
    Set<String> ANNOTATED_IGNORE_CHECK_METHODS = Sets.newConcurrentHashSet();
    
    @MetaData(value = "配置忽略sql检查的方法", note = "GenericMapper子类方法中，注解IgnoreSqlChecker的方法")
    Map<String, String> ANNOED_IGNORE_CHECK_METHODS = Maps.newConcurrentMap();
    
    
    int DEFAULT_CACHE_MAX_SIZE = 100;
    
    int DEFAULT_CACHE_INITIAL_SIZE = 8;
    
    /**
     * <pr>
     * -	indexMetaData-ColumnName:TABLE_CAT - indexInfo-Object:fin_risk]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:TABLE_SCHEM - indexInfo-Object:null]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:TABLE_NAME - indexInfo-Object:t_product]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:NON_UNIQUE - indexInfo-Object:false]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:INDEX_QUALIFIER - indexInfo-Object:]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:INDEX_NAME - indexInfo-Object:PRIMARY]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:TYPE - indexInfo-Object:3]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:ORDINAL_POSITION - indexInfo-Object:1]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:COLUMN_NAME - indexInfo-Object:id]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:ASC_OR_DESC - indexInfo-Object:A]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:CARDINALITY - indexInfo-Object:15]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * -	indexMetaData-ColumnName:PAGES - indexInfo-Object:0]-[c.s.a.d.c.m.p.BadSqlInterceptor.java:229]-[qtp346327277-45]
     * </pr>
     */
    @MetaData(value = "索引描述信息", note = "DataBaseIndex Data")
    @Data
    @EqualsAndHashCode(callSuper = false)
    class DbiData implements Serializable {
        
        private static final long serialVersionUID = 2104945382834621057L;
        
        @MetaData(value = "tableCat")
        private String tableCat;
        
        @MetaData(value = "Schem")
        private String tableSchem;
    
        @MetaData(value = "表名", note = "TABLE_NAME")
        private String tableName;
        
        @MetaData(value = "是否唯一索引", note = "依据Index信息中Non_unique判断, non_unique=false->isUnique=true")
        private boolean isUnique;
        
        @MetaData(value = "INDEX_QUALIFIER")
        private String indexQualifier;
        
        @MetaData(value = "索引名称", note = "INDEX_NAME")
        private String indexName;
        
        @MetaData(value = "索引类型", note = "ResultSet[6]")
        private Integer indexType;
        
        @MetaData(value = "seq_in_index", note = "ORDINAL_POSITION -> ResultSet[7]")
        private String seqInIndex;
        
        @MetaData(value = "列名", note = "COLUMN_NAME -> ResultSet[8]")
        private String columnName;
        
        @MetaData(value = "升/降序", note = "A")
        private String ascOrDesc;
        
        @MetaData(value = "基数", note = "CARDINALITY")
        private String cardinality;
        
        @MetaData(value = "PAGES")
        private String pages;
    }
    
    @MetaData(value = "数据库类型枚举", note = "目前仅支持两种类型库")
    enum DbTypeEnum implements EnumKeyLabelPair {
        
        MySql {
            @Override
            public String getLabel() {
                return "MySql";
            }
        },
        
        Oracle {
            @Override
            public String getLabel() {
                return "Oracle";
            }
        }
    
    }
    
}
