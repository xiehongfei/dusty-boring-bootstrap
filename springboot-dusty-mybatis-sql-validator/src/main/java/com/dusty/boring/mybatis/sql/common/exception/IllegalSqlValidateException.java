/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月18日 16:42
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.exception;

import com.dusty.boring.mybatis.sql.validater.SqlValidateResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 *
 *       <RuntimeException>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月18日 16:42
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月18日 16:42
 * </pre>
 */
@Builder
public class IllegalSqlValidateException extends RuntimeException {
    
    @Setter
    @Getter
    private String code;
    
    @Setter
    @Getter
    private String message;
    
    @Setter
    @Getter
    private String sqlInfo;
    
    @Setter
    @Getter
    private List<SqlValidateResult.Violation> violations;
    
    
    public IllegalSqlValidateException() {
    }
    
    public IllegalSqlValidateException(String code) {
        this.code = code;
        this.setMessage(this.message);
    }
    
    public IllegalSqlValidateException(String code, String message) {
        this.code = code;
        this.setMessage(message);
    }


}
