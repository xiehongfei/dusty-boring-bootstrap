package com.dusty.boring.initail;

import com.dusty.boring.common.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
        log.info("~~ Invoked SpringContextHolderInitalizer#setApplicationContext Method..End");
    }
}
