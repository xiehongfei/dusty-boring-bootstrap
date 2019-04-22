/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 11:04
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 *       <SpringContextHolder初始化>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 11:04
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月29日 11:04
 * </pre>
 */
@Slf4j
@Order(1)
@Component
//@ConditionalOnWebApplication
public class SpringContextHoderInitalizer implements ApplicationContextAware {
    
    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     *
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("~~ Invoked SpringContextHolderInitalizer#setApplicationContext Method..Begin");
        SpringContextHolder.setApplicationContext(applicationContext);
        SpringContextHolder.setEnvironment(SpringContextHolder.getActiveProfile());
        log.info("~~ Invoked SpringContextHolderInitalizer#setApplicationContext Method..End");
    }
}
