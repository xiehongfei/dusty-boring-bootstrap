/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 09:43
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo;

import com.dusty.boring.mybatis.sql.common.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * <pre>
 *
 *       <Demo Application>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 09:43
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月22日 09:43
 * </pre>
 */
@Slf4j
@MapperScan("com.dusty.boring.demo.mapper")
@SpringBootApplication(scanBasePackages = {"com.dusty.boring"})
public class MyBatisSqlValidateDemoApplication {
    
    public static void main(String[] args) {
        
        try {
            SpringApplication.run(MyBatisSqlValidateDemoApplication.class);
        } catch (Exception ex) {
            log.error("\n-\tMyBatisSqlValidateDemoApplication 启动异常", ex);
        }
        
        formatPrint();
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
