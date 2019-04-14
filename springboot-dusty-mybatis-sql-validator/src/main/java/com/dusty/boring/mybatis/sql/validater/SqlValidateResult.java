/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 12:57
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater;

import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 *       <SQL校验结果>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 12:57
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 12:57
 * </pre>
 */
@Getter
@Setter
public class SqlValidateResult {
    
    @MetaData(value = "检查的SQL")
    private String sql;
    
    @MetaData(value = "违规信息")
    private final List<Violation> violations;
    
    public SqlValidateResult(String sql) {
       this(sql, Lists.newArrayList());
    }
    
    public SqlValidateResult(String sql, List<Violation> violations) {
        this.sql = sql;
        this.violations = violations;
    }
    
    @Getter
    @Setter
    @MetaData(value = "违规信息")
    public static class Violation implements Serializable{
    
        private static final long serialVersionUID = -7673550974408964307L;
        
        @MetaData(value = "违规描述")
        private final String message;
        
        @MetaData(value = "违规编码")
        private final String errorCode;
        
        public Violation(String errorCode, String message) {
            
            this.message = message;
            this.errorCode = errorCode;
        }
    }
    
}
