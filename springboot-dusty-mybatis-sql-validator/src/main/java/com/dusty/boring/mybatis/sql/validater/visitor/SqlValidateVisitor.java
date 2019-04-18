/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 11:52
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.visitor;

import com.alibaba.druid.sql.visitor.SQLASTVisitor;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.annotation.MetaData;

import java.util.List;
import com.dusty.boring.mybatis.sql.validater.SqlValidateResult.*;
import com.dusty.boring.mybatis.sql.validater.provider.AbstractSqlValidateProvider;

/**
 * <pre>
 *
 *       <sql校验访问器>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 11:52
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月17日 11:52
 * </pre>
 */
public interface SqlValidateVisitor extends SQLASTVisitor {
    
    @MetaData(value = "数据库类型")
    String getDbType();
    
    @MetaData(value = "获取Sql本地校验服务提供者")
    AbstractSqlValidateProvider getSqlValidateProvider();
    
    @MetaData(value = "获取Sql校验相关配置信息")
    SqlValidatorProperties getSqlValidatorProperties();
    
    @MetaData(value = "获取校验结果")
    List<Violation> getViolations();

    @MetaData(value = "增加Violation")
    void addViolation(Violation violation);
    
}
