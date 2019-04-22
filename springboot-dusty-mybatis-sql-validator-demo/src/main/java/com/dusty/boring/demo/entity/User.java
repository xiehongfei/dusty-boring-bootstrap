/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 10:37
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo.entity;

import lombok.Data;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 10:37
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月22日 10:37
 * </pre>
 */
@Data
public class User {
    
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
