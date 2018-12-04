package com.dusty.boring.common.constants;


import com.dusty.boring.common.annotation.MetaData;

/**
 * <pre>
 *
 *       <Stageloan String常量池>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月30日 15:49
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月30日 15:49
 * </pre>
 */
public interface StringPool {
    
    @MetaData(value = "初始化方法")
    String METHOD_INIT = "init";
    
    @MetaData(value = "instance scope prototype")
    String SCOPE_PROTOTYE = "prototype";
    
    //WarningUserFollowDontVisitListener.class.getCanonicalName();
    @MetaData(value = "未访问任务监听器", note = "WarningUserFollowDontVisitListener.class.getCanonicalName()")
    String DONT_VISIT_JOB_LISTENER = "com.suixingpay.stageloan.marketing.quartz.biz.listener.WarningUserFollowDontVisitListener";
    
    @MetaData(value = "默认ElasticJobExceptionHandler类全限定名")
    String DEFAULT_JOB_EXCEPTION_HANDLER_CLASS = "com.dusty.boring.elastic.core.handler.DefaultElasticJobExceptionHandler";
    
    @MetaData(value = "默认ElasticJobExecutorHandler类全限定名")
    String DEFAULT_JOB_EXECUTOR_HANDLER_CLASS = "com.dusty.boring.elastic.core.handler.DefaultElasticJobExecutorHandler";
    
    @MetaData(value = "默认任务分片策略类全限定名")
    String DEFAULT_JOB_STRATEGY_CLASS = "com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy";
}
