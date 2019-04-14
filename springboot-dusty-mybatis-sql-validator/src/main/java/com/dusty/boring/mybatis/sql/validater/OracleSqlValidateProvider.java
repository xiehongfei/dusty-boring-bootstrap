/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 22:27
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 22:27
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 22:27
 * </pre>
 */
public class OracleSqlValidateProvider extends SqlValidateProvider {
    
    public OracleSqlValidateProvider(SqlValidatorProperties sqlValidatorProperties) {
        this(MyBatisConstPool.DbTypeEnum.Oracle, sqlValidatorProperties);
    }
    
    public OracleSqlValidateProvider(MyBatisConstPool.DbTypeEnum dbType, SqlValidatorProperties sqlValidatorProperties) {
        super(dbType, sqlValidatorProperties);
    }
}
