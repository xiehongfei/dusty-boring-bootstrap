/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 12:57
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 *       <SQL校验结果>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 12:57
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 12:57
 * </pre>
 */
@Getter
@Setter
public class SqlValidateResult {
    
    @MetaData(value = "检查的SQL")
    private String sql;
    
    @MetaData(value = "违规信息")
    private final List<Violation> violations;
    
    public SqlValidateResult(String sql) {
       this(sql, Lists.newArrayList());
    }
    
    public SqlValidateResult(String sql, List<Violation> violations) {
        this.sql = sql;
        this.violations = violations;
    }
    
    @Getter
    @Setter
    @MetaData(value = "违规信息")
    public static class Violation implements Serializable{
    
        private static final long serialVersionUID = -7673550974408964307L;
        
        @MetaData(value = "违规描述")
        private final String message;
        
        @MetaData(value = "违规编码")
        private final String errorCode;
        
        @MetaData(value = "sql语句")
        private final String sql;
        
        public Violation(String errorCode, String message) {
            
            this(errorCode, message, StringUtils.EMPTY);
        }
        
        public Violation(SqlErrorCodeEnum sqlError) {
            
            this(sqlError, StringUtils.EMPTY);
        }
        
        public Violation(String errorCode, String message, String sql) {
            this.sql = sql;
            this.message = message;
            this.errorCode = errorCode;
        }
        
        public Violation(SqlErrorCodeEnum sqlError, String sql) {
            
            this.sql = sql;
            this.errorCode = sqlError.name();
            this.message = sqlError.getLabel();
        }
    }
    
    @Getter
    @Setter
    @Builder
    public static class TableInfo {
        
        @MetaData(value = "表名")
        private String tableName;
        
        @MetaData(value = "表别名")
        private String tableAliasName;
        
        @MetaData(value = "表类型", note = "1-主表 2-子查询表")
        private int tableType;
        
        @MetaData(value = "数据库实例名称")
        private String owner;
    }
    
    @Getter
    @Setter
    @Builder
    public static class TableR2Column {
        
        @MetaData(value = "表名")
        private String tableName;
        
        @MetaData(value = "表别名")
        private String tableAliasName;
        
        @MetaData(value = "列名")
        private String columnName;
        
        @MetaData(value = "操作符")
        private SQLBinaryOperator operator;
        
        @MetaData(value = "比较值")
        private String value;
        
        @MetaData(value = "排序号")
        private Integer order;
    }
    
}
