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
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.visitor.MySqlValidateVisitor;
import junit.framework.TestCase;

import java.util.*;

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
       // sql = "select t.* from t_label t where 1=1";
        sql = "SELECT      distinct a.id \"id\",    a.col \"col\",     a.position \"position\",     a.panel_id \"panelId\"    "
                + "FROM     (select * from db1.view_position_info) a LEFT JOIN db1.view_portal b ON a.panel_id = b.panel_id     "
                + "  LEFT JOIN (select * from db1.view_portal_panel) c  ON a.panel_id = c.panel_id   "
                + " WHERE     b.user_id = ? and     ((b.is_grid='y' and c.param_name='is_hidden' and c.param_value='false') or " +
                "     b.is_grid  != 'y') and b.user_id in (select user_id from db1.table1 where id = 1)    ORDER BY    a.col ASC, a.position ASC";
        
//        sql = "delete from t_label";
//        sql = "update scheme.table set col_1 = 1, col2 = '2'";
//
//        sql = "UPDATE t_price, t_basic_store s " + //
//                "SET purchasePrice = :purchasePrice, operaterId = :operaterId, " + //
//                "    operaterRealName = :operaterRealName, operateDateline = :operateDateline " + //
//                "WHERE goodsId = :goodsId AND s.id = storeId AND s.areaId = :areaId";
//
//        sql = "update t1, t2, t3 inner join t4 using (col_name1, col_name2)\n" +
//                "set t1.value_col = t3.new_value_col, t4.`some-col*` = `t2`.`***` * 2\n" +
//                "where  t1.pk = t2.fk_t1_pk and t2.id = t4.fk_id_entity;";
//
//        sql = "INSERT INTO hz_dev_hb.tb_tmp_cda_opera_281c (target, appeartimes, source_id) SELECT\n" +
//                "           VARCHAR20,\n" +
//                "           count(1)          AS appeartimes,\n" +
//                "           'resource_count1' as source_id\n" +
//                "         FROM hz_dev_hb.tb_fxzx_large t1\n" +
//                "         WHERE 1 = 1 AND VARCHAR20 IS NOT NULL AND\n" +
//                "               MISSIONID =\n" +
//                "               'd2051b6549d9a028e83a8a9ab2c2'\n" +
//                "         GROUP BY VARCHAR20";
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
           
            for (SqlValidateResult.Violation violation : visitor.getViolations()) {
                System.out.println(String.format("\n-\t拦截结果:\t%s-%s-%s", violation.getErrorCode(), violation.getMessage(), violation.getSql()));
            }
        }
    
        System.out.println(String.format("\n-\t拦截表信息:%s-%s", visitor.getTables(), visitor.getColumns()));
    
        final Map<String, SqlValidateResult.TableInfo> tables = visitor.getTables();
        final Iterator<String> iterator = visitor.getTables().keySet().iterator();
        while (iterator.hasNext()) {
            final SqlValidateResult.TableInfo tableInfo = tables.get(iterator.next());
            System.out.println(String.format("\n-\t信息:%s - %s - %s - %s",
                    tableInfo.getTableAliasName(), tableInfo.getTableName(),tableInfo.getTableType(), tableInfo.getOwner()));
        }
    
        final Iterator<String> columnIterator = visitor.getColumns().keySet().iterator();
        final Map<String, SqlValidateResult.TableR2Column> columns = visitor.getColumns();
        System.out.println("\t——— 列名 ———\t———  操作 ———\t——— 表名 ———");
        while (columnIterator.hasNext()) {
            final SqlValidateResult.TableR2Column tableR2Column = columns.get(columnIterator.next());
            System.out.println(String.format("\t%s\t%s\t%s  %s", tableR2Column.getColumnName(), tableR2Column.getOperator(), tableR2Column.getTableAliasName(), tableR2Column.getTableName()));
        }
    
        SQLUtils.parseStatements(sql, MyBatisConstPool.DbTypeEnum.MySql.name());
    }
    
}
