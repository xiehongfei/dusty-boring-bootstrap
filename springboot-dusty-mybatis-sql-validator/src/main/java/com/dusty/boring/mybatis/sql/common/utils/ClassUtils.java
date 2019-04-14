/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:22
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.utils;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:22
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 14:22
 * </pre>
 */
public class ClassUtils {
    
    
    public static Class<?> loadClass(String className) {
        Class<?> clazz = null;
        
        if (className == null) {
            return null;
        }
        
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // skip
        }
        
        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            try {
                clazz = ctxClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                // skip
            }
        }
        
        return clazz;
    }
    
}
