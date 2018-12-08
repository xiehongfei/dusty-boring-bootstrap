package com.dusty.boring.elastic.core.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dusty.boring.common.constants.StringPool;
import com.dusty.boring.initail.SpringContextHoderInitalizer;
import com.dusty.boring.elastic.core.process.ElasticJobAnnotationProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *
 *       <ElasticJob自动配置>
 *
 *           参考资源：https://my.oschina.net/u/719192/blog/506062
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 17:11
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月29日 17:11
 * </pre>
 */
@Configuration
@EnableConfigurationProperties({ZookeeperProperties.class})
public class AnnoOfSimpleJobAutoConfiguration {

    private ZookeeperProperties zookeeperProperties;
    
    public AnnoOfSimpleJobAutoConfiguration(final ZookeeperProperties zookeeperProperties) {
        this.zookeeperProperties = zookeeperProperties;
    }
    
    /**
     * <pre>
     *     注入{@link ZookeeperConfiguration}
     *
     * @return zookeeperConfiguration
     * </pre>
     */
    @ConditionalOnMissingBean
    @Bean
    public ZookeeperConfiguration zookeeperConfiguration() {
        
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(this.zookeeperProperties.getServers(), this.zookeeperProperties.getNamespace());
        zkConfig.setBaseSleepTimeMilliseconds(this.zookeeperProperties.getBaseSleepTimeMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(this.zookeeperProperties.getMaxSleepTimeMilliseconds());
        zkConfig.setMaxRetries(this.zookeeperProperties.getMaxRetries());
        zkConfig.setSessionTimeoutMilliseconds(this.zookeeperProperties.getSessionTimeoutMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(this.zookeeperProperties.getConnectionTimeoutMilliseconds());
        
        if (null != this.zookeeperProperties.getDigest()
                && this.zookeeperProperties.getDigest().trim().length() > 0) {
            zkConfig.setDigest(this.zookeeperProperties.getDigest());
        }
        
        return zkConfig;
    }
    
    /**
     * <pre>
     *     注入{@link ZookeeperConfiguration}
     *
     * @param zkConfiguration zkConfig
     * @return zkRegistryCenter
     * </pre>
     */
    @Bean(initMethod = StringPool.METHOD_INIT)
    @ConditionalOnMissingBean
    @ConditionalOnBean(ZookeeperConfiguration.class)
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zkConfiguration) {
        return new ZookeeperRegistryCenter(zkConfiguration);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SpringContextHoderInitalizer springContextHoderInitalizer() {
        return new SpringContextHoderInitalizer();
    }
    
    
    @Configuration
    protected static class AnnotationConfiguration {
    
        @Bean
        public ElasticJobAnnotationProcessor annotationProcessor(ApplicationContext applicationContext) {
            return new ElasticJobAnnotationProcessor(applicationContext);
        }
    }
    
//    @Configuration
//    @EnableConfigurationProperties(ElasticJobProperties.class)
//    protected static class BeansOfSimpleJobConfiguration {
//
//        private final ElasticJobProperties elasticJobProperties;
//
//        public BeansOfSimpleJobConfiguration(ElasticJobProperties elasticJobProperties) {
//            this.elasticJobProperties = elasticJobProperties;
//        }
//
//
//        @Bean(initMethod = "initElasticSimpleJobs")
//        public ElasticLiteJobBeansProcessor beansProcessor() {
//            return new ElasticLiteJobBeansProcessor(elasticJobProperties);
//        }
//
//        /**
//         * <pre>
//         *     默认监听器
//         *
//         * @return
//         * </pre>
//         */
//        @Bean
//        @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//        public DefaultSimpleJobLitener defaultSimpleJobLitener() {
//            return new DefaultSimpleJobLitener();
//        }
//
//    }

}
