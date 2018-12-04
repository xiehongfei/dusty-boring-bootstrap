package com.dusty.boring.elastic.core.process;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dusty.boring.common.constants.StringPool;
import com.dusty.boring.elastic.core.annotation.ElasticJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.util.EnumMap;
import java.util.List;

/**
 * <pre>
 *       <ElasticJob相关注解 {@link ElasticJob} 处理类>
 *
 *           资料：https://github.com/elasticjob/elastic-job-lite
 *           处理注解ElasticJob形式定义的ElasticJob作业信息
 *           Steps:
 *               - 1.构造JobCoreConfiguration Bean信息结构；
 *               - 2.根据（1）及{@link ElasticJob#jobType()}
 *               分别构造作业类型{@link com.dangdang.ddframe.job.api.JobType#SIMPLE} 及 {@link com.dangdang.ddframe.job.api.JobType#DATAFLOW}相应的Configuration基类；
 *               - 3.根据以上，构造{@link LiteJobConfiguration};
 *               - 4.根据以上，初始化{@link SpringJobScheduler}
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 16:48
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月29日 16:48
 * </pre>
 */
@Slf4j
public class ElasticJobAnnotationProcessor implements BeanPostProcessor {
    
    private final ApplicationContext applicationContext;
    
    public ElasticJobAnnotationProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    /**
     * <pre>
     *
     *     实例化、依赖注入完毕，在调用显式的初始化之前完成一些定制的初始化任务
     *
     * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     *
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     *
     *
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * </pre>
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
       
        //~~ 1.获取Bean，是否注解了ElasticJob ~~
        ElasticJob elasticJob = bean.getClass().getAnnotation(ElasticJob.class);
        if (null == elasticJob)
            return bean;
        
        log.info("==== 开始处理ElasticJobBean-{},注册作业到作业调度中心。", bean.getClass().getCanonicalName());
        /** ~~~
         *  构造{@link JobCoreConfiguration} BeanDefinition 信息
         *  ~~~
         */
        BeanDefinition jobCoreConfigBean = buildElasticJobCoreBeanDefinition(elasticJob);
        BeanDefinition jobWithinTypeBean;
        switch (elasticJob.jobType()) {
            /**~~~
             * 构造{@link SimpleJobConfiguration} BeanDefinition信息
             * ~~~
             */
            case SIMPLE:
                jobWithinTypeBean = buildElasticSimpleJobConfigBeanDefinition(elasticJob, jobCoreConfigBean, bean);
                break;
                
            case DATAFLOW:
                jobWithinTypeBean = buildDataFlowJobConfigurationBeanDefinition(elasticJob, jobCoreConfigBean, bean);
                break;
                
            case SCRIPT:
                throw new UnsupportedOperationException("暂不支持 ScriptJob Mode，敬请期待。");
                
                default:
                    throw new IllegalArgumentException("参数错误，ElasticJob jobType不能为空，或取值错误。");
        }
        
        BeanDefinitionBuilder liteJobConfigBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(LiteJobConfiguration.class);
        liteJobConfigBeanBuilder.addConstructorArgValue(jobWithinTypeBean);
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.monitorExecution());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.maxTimeDiffSeconds());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.monitorPort());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.jobShardingStrategyClass());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.reconcileIntervalMinutes());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.disabled());
        liteJobConfigBeanBuilder.addConstructorArgValue(elasticJob.overwrite());
        
        BeanDefinition liteJobConfigBean = liteJobConfigBeanBuilder.getBeanDefinition();
    
        /**~~~
         *  根据{@link ElasticJob}注解内容及以上构造
         *  初始化{@link SpringJobScheduler}
         *    {@link DefaultListableBeanFactory#registerBeanDefinition(String, BeanDefinition)}
         * ~~~
         */
        //~~ 编码方式如下 ~~
        //使用BeanDefinition的方式进行实例化
        // new SpringJobScheduler(xxWarningJob,
        //  zkRegCenter,
        //  getXxxLiteJobConfig(),
        //  jobEventConfig,
        //  new SxpElasticBasicListener());
    
        BeanDefinitionBuilder springJobSchedulerBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
        springJobSchedulerBeanBuilder.setInitMethodName("init");
        springJobSchedulerBeanBuilder.addConstructorArgReference(beanName);
        springJobSchedulerBeanBuilder.addConstructorArgReference(elasticJob.register());
        springJobSchedulerBeanBuilder.addConstructorArgValue(liteJobConfigBean);
        if (elasticJob.eventTraceRdbDataSource().trim().length() > 0) {
            //~~ 设置作业数据库事件配置 ~~
            BeanDefinitionBuilder rdbDataSourceBuilder = BeanDefinitionBuilder.rootBeanDefinition(elasticJob.eventTraceRdbDataSource());
            rdbDataSourceBuilder.addConstructorArgReference(elasticJob.eventTraceRdbDataSource());
            springJobSchedulerBeanBuilder.addConstructorArgValue(rdbDataSourceBuilder.getBeanDefinition());
        }
        //~~ 任内执行监听器 BEGIN ~~
        List listeners = new ManagedList(2);
        if (elasticJob.normalListener().trim().length() > 0) {
            BeanDefinitionBuilder normalListenerBuilder = BeanDefinitionBuilder.rootBeanDefinition(elasticJob.normalListener());
            normalListenerBuilder.setScope(StringPool.SCOPE_PROTOTYE);
            listeners.add(normalListenerBuilder.getBeanDefinition());
        }
    
        if (elasticJob.distributedOnceListener().trim().length() > 0) {
            BeanDefinitionBuilder onceListenerBuilder = BeanDefinitionBuilder.rootBeanDefinition(elasticJob.distributedOnceListener());
            onceListenerBuilder.addConstructorArgValue(elasticJob.startedTimeoutMilliseconds());
            onceListenerBuilder.addConstructorArgValue(elasticJob.completedTimeoutMilliseconds());
            onceListenerBuilder.setScope(StringPool.SCOPE_PROTOTYE);
            listeners.add(onceListenerBuilder.getBeanDefinition());
        }
        springJobSchedulerBeanBuilder.addConstructorArgValue(listeners);
        //~~ 任内执行监听器 END  ~~
        
        //~~ 其它属性赋值 ~~
        springJobSchedulerBeanBuilder.setLazyInit(false);
    
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
        String registerBeanName = String.format("%sJobScheduler", beanName);
        beanFactory.registerBeanDefinition(registerBeanName, springJobSchedulerBeanBuilder.getBeanDefinition());
        beanFactory.getBean(registerBeanName);
        
        return bean;
    }
    
    /**
     * <pre>
     *     创建DataflowJobConfiguration Bean
     *     @see DataflowJobConfiguration
     *
     * @param elasticJob        elastic注解
     * @param jobCoreConfigBean jobCoreConfigurationBeanDefinition
     * @param bean              待容器加载的Bean
     * @return dataFlowJobConfigurationBeanDefinition
     * </pre>
     */
    private BeanDefinition buildDataFlowJobConfigurationBeanDefinition(ElasticJob elasticJob,
                                                                       BeanDefinition jobCoreConfigBean,
                                                                       Object bean) {
        BeanDefinitionBuilder dataFlowJobConfigBeanBuilder
                = BeanDefinitionBuilder.rootBeanDefinition(DataflowJobConfiguration.class);
        
        dataFlowJobConfigBeanBuilder.addConstructorArgValue(jobCoreConfigBean);
        
        if (null == elasticJob.jobClass() || elasticJob.jobClass().trim().length() == 0) {
            //获取所传类从java语言规范定义的格式输出
            dataFlowJobConfigBeanBuilder.addConstructorArgValue(bean.getClass().getCanonicalName());
        } else {
            dataFlowJobConfigBeanBuilder.addConstructorArgValue(elasticJob.jobClass());
        }
        
        dataFlowJobConfigBeanBuilder.addConstructorArgValue(true);
        
        throw new UnsupportedOperationException("暂不支持 Elastic Cloud Job Mode， 敬请期待。");
    }
    
    /**
     * <pre>
     *     创建Elastic SimpleJobConfiguration Bean
     *     @see SimpleJobConfiguration
     *
     * @param elasticJob        elasticJob注解
     * @param jobCoreConfigBean jobCoreConfiguration
     * @param bean              加载的bean
     * @return SimpleJobConfiguration beanDefinition
     * </pre>
     */
    private BeanDefinition buildElasticSimpleJobConfigBeanDefinition(ElasticJob elasticJob,
                                                                     BeanDefinition jobCoreConfigBean,
                                                                     Object bean) {
        
        BeanDefinitionBuilder simpleJobConfigBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(SimpleJobConfiguration.class);
        simpleJobConfigBeanBuilder.addConstructorArgValue(jobCoreConfigBean);
        if (null == elasticJob.jobClass() || elasticJob.jobClass().trim().length() == 0) {
            //获取所传类从java语言规范定义的格式输出
            simpleJobConfigBeanBuilder.addConstructorArgValue(bean.getClass().getCanonicalName());
        } else {
            simpleJobConfigBeanBuilder.addConstructorArgValue(elasticJob.jobClass());
        }
        
        return simpleJobConfigBeanBuilder.getBeanDefinition();
    }
    
    /**
     * <pre>
     *     根据ElasticJob注解信息，创建JobCoreConfiguration Bean
     *     @see JobCoreConfiguration
     *
     * @param  elasticJob bean信息源注解
     * @return beanDefinition信息
     * </pre>
     */
    private BeanDefinition buildElasticJobCoreBeanDefinition(ElasticJob elasticJob) {
    
        Assert.hasLength(elasticJob.corn(), "elastic corn is required");
        Assert.hasLength(elasticJob.jobName(), "elastic job name is required");
        
        //~~ 构造 JobProperties BeanDefinition ~~
        BeanDefinitionBuilder elasticJobPropertiesBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(JobProperties.class);
        EnumMap map = new EnumMap(JobProperties.JobPropertiesEnum.class);
        map.put(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER, elasticJob.executorServiceHandler());
        map.put(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER, elasticJob.jobExceptionHandler());
        //~~ EnumMap写入JopProperties BeanDefinition ~~
        elasticJobPropertiesBeanBuilder.addConstructorArgValue(map);
        
        //~~ 构造 JobCoreConfiguration BeanDefinition ~~
        BeanDefinitionBuilder elasticJobCoreBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(JobCoreConfiguration.class);
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.jobName());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.corn());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.shardingTotalCount());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.shardingItemParameters());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.jobParameter());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.failover());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.misfire());
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJob.description());
        //~~ JobProperties 注入到 JobCoreConfiguration ~~
        elasticJobCoreBeanBuilder.addConstructorArgValue(elasticJobPropertiesBeanBuilder.getBeanDefinition());
        
        return elasticJobCoreBeanBuilder.getBeanDefinition();
    }
    
    /**
     * <pre>
     *
     *     实例化、依赖注入、初始化完毕时执行
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     *
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     *
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * @see FactoryBean
     * </pre>
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean; //不可返回null
    }
}
