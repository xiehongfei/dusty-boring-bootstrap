/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 22:27
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.provider;

import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;

/**
 * <pre>
 *
 *       <Oracle Validate Provider>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 22:27
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 22:27
 * </pre>
 */
public class OracleValidateProvider extends AbstractSqlValidateProvider {
    
    public OracleValidateProvider(SqlValidatorProperties sqlValidatorProperties) {
        this(MyBatisConstPool.DbTypeEnum.Oracle, sqlValidatorProperties);
    }
    
    public OracleValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        super(dbType, sqlValidatorProperties);
    }
    
    @Override
    public SQLStatementParser createParser(String sql) {
       
        return new OracleStatementParser(sql);
    }
    
    @Override
    public SqlValidateVisitor createValidateVisitor() {
        return null;
    }
}
