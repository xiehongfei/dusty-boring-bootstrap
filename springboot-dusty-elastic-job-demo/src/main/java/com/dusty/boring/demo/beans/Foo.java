/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:17
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * <pre>
 *
 *       <作业数据>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:17
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月08日 11:17
 * </pre>
 */
@Data
public class Foo implements Serializable {
    
    private String id = UUID.randomUUID().toString().replace("-", "");
}
