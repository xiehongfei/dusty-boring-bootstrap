/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:58
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.autoconfig;

import com.dusty.boring.mybatis.sql.intercept.BadSqlValidateIntercepter;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * <pre>
 *
 *       <Sql检查自动配置>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:58
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月13日 21:58
 * </pre>
 */
@Slf4j
@ConditionalOnBean(DataSource.class)
@ConditionalOnClass({ SqlSessionFactory.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(SqlValidatorProperties.class)
public class SqlValidatorAutoConfigure {

    private final SqlValidatorProperties sqlValidatorProperties;
    
    @PostConstruct
    public void autoConfigCheck() {
        
        log.info("\n-\t 当前拦截器参数:{}", sqlValidatorProperties.toString());
        
    }
    
    public SqlValidatorAutoConfigure(SqlValidatorProperties sqlValidatorProperties) {
        this.sqlValidatorProperties = sqlValidatorProperties;
    }
    
    /**
     * <pre>
     *     注入SQL拦截器
     *
     * @return  拦截器实例
     * </pre>
     */
    @Bean
    public BadSqlValidateIntercepter injectBadSqlValidateIntercepterHandler() {
        return new BadSqlValidateIntercepter();
    }

}
