/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 17:52
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.pool;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 17:52
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月15日 17:52
 * </pre>
 */
public enum SqlErrorCodeEnum implements EnumKeyLabelPair {
    
    
    SQL9000 {
        @Override
        public String getLabel() {
            return "拒绝执行的SQL";
        }
    },
    
    SQL9001 {
        @Override
        public String getLabel() {
            return "禁止执行DDL命令";
        }
    },
    
    SQL9002 {
        @Override
        public String getLabel() {
            return "禁止执行truncate命令";
        }
    },
    
    SQL9003 {
        @Override
        public String getLabel() {
            return "禁止执行无Where条件的查询";
        }
    },
    
    SQL9004 {
        @Override
        public String getLabel() {
            return "禁止执行无Where条件的删除";
        }
    },
    
    SQL9005 {
        @Override
        public String getLabel() {
            return "禁止无Where条件的更新操作";
        }
    },
    
    SQL9006 {
        @Override
        public String getLabel() {
            return "禁止执行未使用索引的查询语句";
        }
    }
    
}
