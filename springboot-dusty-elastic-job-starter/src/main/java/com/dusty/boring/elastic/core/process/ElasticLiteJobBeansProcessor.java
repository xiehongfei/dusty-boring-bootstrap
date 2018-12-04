package com.dusty.boring.elastic.core.process;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dusty.boring.common.context.SpringContextHolder;
import com.dusty.boring.elastic.core.annotation.ElasticJob;
import com.dusty.boring.elastic.core.autoconfigure.ElasticJobProperties;
import com.dusty.boring.elastic.core.listener.DefaultSimpleJobLitener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * <pre>
 * <ElasticLiteJobBeans Process>
 *
 *     补充处理ElasticSimpleJob作业注册，
 *     只针对无{@link ElasticJob}注解的SimpleJob子类进行注册
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 15:51
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 15:51
 * </pre>
 */
@Slf4j
public final class ElasticLiteJobBeansProcessor {
    
    private final ElasticJobProperties elasticJobProperties;
    
    public ElasticLiteJobBeansProcessor(ElasticJobProperties elasticJobProperties) {
        this.elasticJobProperties = elasticJobProperties;
    }
    
    public void initElasticSimpleJobs() {
    
        //~~ 获取Zookeeper注册中心配置 ~~
        ZookeeperRegistryCenter zookRegCenter = SpringContextHolder.getBean(ZookeeperRegistryCenter.class);
        
        //~~ 获取DustyElasticJob配置 ~~
        Map<String, ElasticJobProperties.DustySimpleJob> simpleJobMap = elasticJobProperties.getCachedSimpleJobParams();
        
        //~~ 获取继承自SimpleJob的Bean ~~
        Map<String, SimpleJob> simpleJobBeans = SpringContextHolder.getBeansOfType(SimpleJob.class);
        for (Map.Entry<String, SimpleJob> simpleJob : simpleJobBeans.entrySet() ) {
            
            SimpleJob job = simpleJob.getValue();
            if (null != job.getClass().getAnnotation(ElasticJob.class)) {
                //~~ 如果已被ElasticJob 注解，则本次作业忽略操作 ~~
                log.warn(String.format("skip process simpleJob%s, it will process by another way", job.getClass().getName()));
                continue;
            }
            
            String className = job.getClass().getSimpleName();
            ElasticJobProperties.DustySimpleJob prop = simpleJobMap.get(className);
            Assert.notNull(prop, "SimpleJob参数为空，无法实例化Job");
            
            //~~ Elastic Lite Job 相关处理 Begin ~~
            //~~ 定义Job名称 ~~
            String jobName = String.format("%sScheduler", className);
            
            JobCoreConfiguration jobCoreConfig
                    = JobCoreConfiguration.newBuilder(jobName, prop.getCorn(),
                    prop.getShardingTotalCount()).shardingItemParameters(prop.getShardingItemParameters()).build();
            
            //~~ 构造LiteJobConfiguration ~~
            SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(jobCoreConfig, jobName);
            LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
            
            //~~ SpringJobScheduler初始化 ~~
            SpringJobScheduler springJobScheduler = new SpringJobScheduler(job, zookRegCenter, liteJobConfig, SpringContextHolder.getBean(DefaultSimpleJobLitener.class));
            springJobScheduler.init();
            //~~ Elastic Lite Job 相关处理 End    ~~
        }
        
    }
    
}
