/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:14
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *
 *       <忽略SQL检查注解>
 *
 *           - 用于标注需忽略SQL检查的方法级注解
 *           - 记录相关开发/审核人员
 *
 *
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:14
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月13日 21:14
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSqlChecker {

    @MetaData(value = "开发人员")
    String developor();
    
    @MetaData(value = "复核者")
    String reviewer();

}
