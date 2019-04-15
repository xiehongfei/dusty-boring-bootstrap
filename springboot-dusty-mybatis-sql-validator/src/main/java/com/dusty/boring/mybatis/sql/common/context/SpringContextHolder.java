package com.dusty.boring.mybatis.sql.common.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *     <SpringContextHolder>
 *       Spring Context 持有工具类
 *       需要由使用方对其初始化
 * </pre>
 */
public class SpringContextHolder {
    
    private SpringContextHolder() {
        //私有构造
    }

    private static String ENV;
    
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }
    
    public static String getEnvironment() {
        
        if (StringUtils.isEmpty(ENV)) {
            ENV = getActiveProfile();
        }
        
        return ENV;
    }
    
    public static void setEnvironment(String env) {
        ENV = env;
    }

    /**
     * <pre>
     *     发布事件
     *
     * @param event 事件
     * </pre>
     */
    public static void eventPublish(Object event) {
        applicationContext.publishEvent(event);
    }
    
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {

        return applicationContext.getBean(requiredType);

    }
    
    /**
     * <pre>
     *     获取指定类型的Bean
     *
     * @param beanName beanName
     * @param clazz    指定的类型
     * @param <T>      泛型T
     * @return         T t
     * </pre>
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
    
    /**
     * <pre>
     *     获取当前profile
     *     默认获取第一个
     *
     * @return profile
     * </pre>
     */
    public static String getActiveProfile() {
        
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }
    
    
    /**
     * <pre>
     *     从Spring容器中获取指定类型的Bean
     *
     * @param clazz 指定类型
     * @param <T>   返回类型泛型
     * @return 结果map
     * </pre>
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }
    
    
    /**
     * <pre>
     *     获取指定注解的Bean
     *
     * @param  annType 指定注解类型
     * @return 结果map
     * </pre>
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annType) {
        return applicationContext.getBeansWithAnnotation(annType);
    }
    
    /**
     * <pre>
     *     当前是否开发/测试模式
     *
     * @return boolean true|false
     * </pre>
     */
    public static boolean isDemoMode() {
        return  isTestMode() || isDevMode();
    }
    
    /**
     * <pre>
     *     当前是否开发环境
     *
     * @return boolean
     * </pre>
     */
    public static boolean isDevMode() {
        return "dev".equalsIgnoreCase(getActiveProfile());
    }
    
    /**
     * <pre>
     *     当前是否测试环境
     *
     * @return boolean
     * </pre>
     */
    public static boolean isTestMode() {
        return "test".equalsIgnoreCase(getActiveProfile());
    }
    
    
    /**
     * <pre>
     *     获取BeanDeinitionRegistry
     *
     * @return BeanDeinitionRegistry实例
     * </pre>
     */
    public static BeanDefinitionRegistry getBeanDeinitionRegistry() {
        
        Assert.notNull(applicationContext, "applicationContext获取失败!");
        
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        return (DefaultListableBeanFactory)configurableApplicationContext.getBeanFactory();
    }
    
}