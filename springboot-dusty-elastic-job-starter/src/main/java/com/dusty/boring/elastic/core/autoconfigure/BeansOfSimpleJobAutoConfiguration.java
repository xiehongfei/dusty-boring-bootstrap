package com.dusty.boring.elastic.core.autoconfigure;

import com.dusty.boring.elastic.core.listener.DefaultSimpleJobLitener;
import com.dusty.boring.elastic.core.process.ElasticLiteJobBeansProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <pre>
 * <Bean方式注册作业处理器>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 17:59
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 17:59
 * </pre>
 */
@Configuration
@ConditionalOnProperty(value = "dusty.elasticjob.enabled-bean-mode", havingValue = "true")
@EnableConfigurationProperties(ElasticJobProperties.class)
public class BeansOfSimpleJobAutoConfiguration {
    
    private final ElasticJobProperties elasticJobProperties;
    
    public BeansOfSimpleJobAutoConfiguration(ElasticJobProperties elasticJobProperties) {
        this.elasticJobProperties = elasticJobProperties;
    }
    
    
    @Bean(initMethod = "initElasticSimpleJobs")
    public ElasticLiteJobBeansProcessor beansProcessor() {
        return new ElasticLiteJobBeansProcessor(elasticJobProperties);
    }
    
    /**
     * <pre>
     *     默认监听器
     *
     * @return
     * </pre>
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultSimpleJobLitener defaultSimpleJobLitener() {
        return new DefaultSimpleJobLitener();
    }
    
}
