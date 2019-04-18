/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 19:24
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.visitor.MySqlValidateVisitor;
import junit.framework.TestCase;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 19:24
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月17日 19:24
 * </pre>
 */
public class SqlValidateProviderTest extends TestCase {
    
    
    public void test() {
        
        String sql = "select * from t_label t where t.label_id and t.id between 2 and 4";
        //sql = "drop table t_label";
        sql = "select * from t_label";
        sql = "SELECT      distinct a.id \"id\",    a.col \"col\",     a.position \"position\",     a.panel_id \"panelId\"    "
                + "FROM     (select * from view_position_info) a LEFT JOIN db1.view_portal b ON a.panel_id = b.panel_id     "
                + "  LEFT JOIN (select * from view_portal_panel) c  ON a.panel_id = c.panel_id   "
                + " WHERE     b.user_id = ? and     ((b.is_grid='y' and c.param_name='is_hidden' and c.param_value='false') or " +
                "     b.is_grid  != 'y') and b.user_id in (select user_id from table1 where id = 1)    ORDER BY    a.col ASC, a.position ASC";
        
        SqlValidatorProperties properties = new SqlValidatorProperties();
        properties.setEnvProfiles(new SqlValidatorProperties.EnvProfiles());
        properties.setMySqlValidItems(new SqlValidatorProperties.MySqlValidateItems());
        MySqlValidateProvider provider = new MySqlValidateProvider(properties);
        MySqlValidateVisitor visitor = new MySqlValidateVisitor(provider);
    
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        final List<SQLStatement> sqlStatements = parser.parseStatementList();
        for (SQLStatement statement : sqlStatements) {
            statement.accept(visitor);
        }
        
        if (Objects.nonNull(visitor.getViolations()) && visitor.getViolations().size() > 0) {
            final SqlValidateResult.Violation violation = visitor.getViolations().get(0);
            System.out.println(String.format("\n-\t拦截结果:%s-%s", violation.getErrorCode(), violation.getMessage()));
        }
        
        SQLUtils.parseStatements(sql, MyBatisConstPool.DbTypeEnum.MySql.name());
    }
    
}
