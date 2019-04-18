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
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.visitor.MySqlValidateVisitor;
import junit.framework.TestCase;

import java.util.List;

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
        String sql = "select * from t_label t where t.label_id";
        SqlValidatorProperties properties = new SqlValidatorProperties();
        MySqlValidateProvider provider = new MySqlValidateProvider(properties);
        MySqlValidateVisitor visitor = new MySqlValidateVisitor(provider);
    
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        final List<SQLStatement> sqlStatements = parser.parseStatementList();
        for (SQLStatement statement : sqlStatements) {
            statement.accept(visitor);
        }
        
        SQLUtils.parseStatements(sql, MyBatisConstPool.DbTypeEnum.MySql.name());
    }
    
}
