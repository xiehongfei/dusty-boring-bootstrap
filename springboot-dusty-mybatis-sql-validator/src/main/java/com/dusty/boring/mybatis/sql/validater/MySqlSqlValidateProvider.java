/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 09:04
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;

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
public class MySqlSqlValidateProvider extends SqlValidateProvider {
    
    public MySqlSqlValidateProvider(SqlValidatorProperties sqlValidatorProperties) {
        this(MyBatisConstPool.DbTypeEnum.MySql, sqlValidatorProperties);
    }
    
    public MySqlSqlValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        super(dbType, sqlValidatorProperties);
    }
    
}
