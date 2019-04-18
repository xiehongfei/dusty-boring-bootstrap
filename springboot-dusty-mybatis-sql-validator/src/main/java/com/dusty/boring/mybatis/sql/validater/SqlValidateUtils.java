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
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;

import static com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties.MySqlValidateItems;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.VC9999;

/**
 * <pre>
 *
 *       <功能详细描述>
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
            errorCode = VC9999;
            valid = validateItems.isEnableDdlDrop();
            denyMessage = "禁止使用DDL语法: drop";
        } else if (x instanceof SQLTruncateStatement) {
            errorCode = VC9999;
            denyMessage = "禁止使用truncate命令";
        } else if (x instanceof SQLAlterStatement) {
            errorCode = VC9999;
            denyMessage = "禁止使用alter命令";
        } else if (x instanceof SQLCommentStatement) {
            errorCode = VC9999;
            valid = validateItems.isEnableDdlComment();
            denyMessage = "禁止使用DDL语法: comment";
        } else if (x instanceof SQLExplainStatement) {
            errorCode = VC9999;
            valid = validateItems.isEnableExplain();
            denyMessage = "禁止使用explain命令";
        } else if (x instanceof MySqlLockTableStatement) {
            
            errorCode = VC9999;
            valid = validateItems.isEnableLockTable();
            denyMessage = "禁止使用lock table命令";
        }
        
        if (!valid) {
            visitor.addViolation(new SqlValidateResult.Violation(errorCode, denyMessage));
        }
    }
    
}
