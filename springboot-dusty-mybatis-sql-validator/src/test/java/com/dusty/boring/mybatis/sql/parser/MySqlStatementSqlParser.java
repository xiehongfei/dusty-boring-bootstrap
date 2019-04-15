/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 15:25
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.wall.WallProvider;
import com.alibaba.druid.wall.spi.MySqlWallVisitor;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 15:25
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月15日 15:25
 * </pre>
 */
public class MySqlStatementSqlParser extends TestCase {
    
    public void test() {
        
        String sql = "select * from t_label t where t.label_code = ? and t.label_name like '%?%'";
               sql = "select tmp.* from (select t.label_id, t.label_name, t.status from t_label t where t.create_date >= ? and t.create_date <= ?) tmp where tmp.label_name = ?";
               
        final MySqlStatementParser mySqlStatementParser = new MySqlStatementParser(sql
                , SQLParserFeature.EnableSQLBinaryOpExprGroup
                , SQLParserFeature.StrictForWall
        );
        final List<SQLStatement> sqlStatements = mySqlStatementParser.parseStatementList();
    
        Assert.assertTrue(Objects.nonNull(sqlStatements) && sqlStatements.size() == 1);
        
        final SQLStatement sqlStatement = sqlStatements.get(0);
        
        if (sqlStatement instanceof SQLSelectStatement) {
            validateWhereExpr(sqlStatement);
        } else {
            System.out.println("\n-\t Not Select");
        }
    
        mySqlStatementParser.getLexer().setCommentHandler(WallProvider.WallCommentHandler.instance);
        //new MySqlWallVisitor();
        
        SQLSelect select = new SQLSelect();
        MySqlSelectQueryBlock queryBlock = new MySqlSelectQueryBlock();
        queryBlock.addSelectItem(new SQLPropertyExpr(new SQLVariantRefExpr("@@session"), "tx_read_only"));
        select.setQuery(queryBlock);
        SQLSelectStatement stmt = new SQLSelectStatement(select);
        
    }
    
    private void validateWhereExpr(SQLStatement sqlStatement) {
    
        final SQLSelectQuery sqlQuery = ((SQLSelectStatement) sqlStatement).getSelect().getQuery();
        if (sqlQuery instanceof MySqlSelectQueryBlock) {
            SQLExpr where = ((MySqlSelectQueryBlock) sqlQuery).getWhere();
            //if (where instanceof )
            final SQLTableSource from = ((MySqlSelectQueryBlock) sqlQuery).getFrom();
            if (from instanceof SQLSubqueryTableSource) {
                final SQLSelectQuery query = ((SQLSubqueryTableSource) from).getSelect().getQuery();
                if (query instanceof MySqlSelectQueryBlock) {
                    final SQLTableSource from1 = ((MySqlSelectQueryBlock) query).getFrom();
                    where = ((MySqlSelectQueryBlock) query).getWhere();
                    if (where instanceof SQLBinaryOpExpr) {
                        where.getChildren();
                    }
                }
                
            } else if (from instanceof SQLExprTableSource) {
                ((SQLExprTableSource) from).getName();
                ((SQLExprTableSource) from).getSchema();
                ((SQLExprTableSource) from).getSchemaObject();
            }
        
            if (Objects.isNull(where)) {
                System.out.println("\n-\tWhere为空！");
            } else {
                System.out.println(String.format("\n-\tWhere不为空, Where=%s", where.toString()));
            }
            //System.out.println(JSONUtils.toJSONString(where));
        }
        
    }
    
    public void test1() {
        
        System.out.println(SQLDropStatement.class.isAssignableFrom(SQLDropTableStatement.class));
    
        SQLDropTableStatement sqlDropTableStatement = new SQLDropTableStatement();
        SQLDropIndexStatement sqlDropIndexStatement = new SQLDropIndexStatement();
        
        System.out.println(SQLDropStatement.class.isAssignableFrom(sqlDropTableStatement.getClass()));
        System.out.println(SQLDropStatement.class.isAssignableFrom(sqlDropIndexStatement.getClass()));
    
        System.out.println(sqlDropIndexStatement instanceof SQLDropStatement);
    }
    
    public void test2() {
    
    }
}
