/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 09:04
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.provider;

import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.validater.visitor.MySqlValidateVisitor;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;

/**
 * <pre>
 *
 *       <MySql-SqlValidate Provider>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 09:04
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 09:04
 * </pre>
 */
public class MySqlValidateProvider extends AbstractSqlValidateProvider {
    
    public MySqlValidateProvider(SqlValidatorProperties sqlValidatorProperties) {
        this(MyBatisConstPool.DbTypeEnum.MySql, sqlValidatorProperties);
    }
    
    public MySqlValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        super(dbType, sqlValidatorProperties);
    }
    
    @Override
    public SQLStatementParser createParser(String sql) {
        return new MySqlStatementParser(sql);
    }
    
    @Override
    public SqlValidateVisitor createValidateVisitor() {
        
        return new MySqlValidateVisitor(this);
    }
    
}
