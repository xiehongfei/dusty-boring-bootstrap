package com.dusty.boring.mybatis.sql.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * <pre>
 *
 *       <加密工具>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年03月26日 18:16
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年03月26日 18:16
 * </pre>
 */
public class EncryptUtils {
    
    private EncryptUtils() {
    
    }
    
    /**
     * <pre>
     *     MD5加密
     *
     * @param  key 待加密内容
     * @return res   加密结果
     * </pre>
     */
    public static String md5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    /**
     * <pre>
     *     带有盐值的MD5加密方法
     *
     * @param input  待加密内容
     * @param salt   注入盐值
     * @return res   加密结果
     * </pre>
     */
    public static String md5(String input, String salt) {
        
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        input = input + salt;
        return md5(input);
    }
    
   
    
    
    /**
     * <pre>
     *     Base64编码
     *
     * @param  input 待编码内容
     * @return res   编码结果
     * </pre>
     */
    public static String base64(String input) {
    
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        
        return Base64Utils.encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static void main(String[] args) {
        String sql = "SELECT  id,up_id,lable_code,lable_name,lable_level,lable_type,remark,status,creator,create_date,updator,update_date FROM t_lable  WHERE  up_id  =?  AND status  =?";
        
        System.out.println(base64(md5(sql)));
        System.out.println(base64(md5(sql)));
        System.out.println(base64(md5(sql)));
        
    }
    
}
