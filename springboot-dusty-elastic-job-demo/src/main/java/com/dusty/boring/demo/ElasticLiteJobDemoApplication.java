/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月03日 22:40
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo;

import com.dusty.boring.common.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * <pre>
 *
 *       <Elastic Lite Job Demo Application>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月03日 22:40
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月03日 22:40
 * </pre>
 */
@Slf4j
@SpringBootApplication
public class ElasticLiteJobDemoApplication {
    
    public static void main(String[] args) {
        
        try {
            
            SpringApplication.run(ElasticLiteJobDemoApplication.class, args);
            formatPrint();
            
        } catch (Exception ex) {
            //catch启动异常信息，方便排除问题
            ex.printStackTrace();
            log.error("**** ElasticLiteJobDemoApplication启动失败", ex);
        }
    }
    
    private static void formatPrint() {
        
        String profile = SpringContextHolder.getActiveProfile();
        Environment env = SpringContextHolder.getBean(Environment.class);
        log.info(
                "\n    ~~~~ env.port    - {} ~~~~" +
                "\n    ~~~~ env.profile - {} ~~~~" +
                "\n    ~~~~ env.name - stageloan-marketing ~~~~", env.getProperty("server.port"), profile);
    }
    
}
