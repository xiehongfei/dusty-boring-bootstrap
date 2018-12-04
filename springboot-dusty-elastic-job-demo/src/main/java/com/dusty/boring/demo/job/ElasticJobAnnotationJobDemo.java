package com.dusty.boring.demo.job;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dusty.boring.common.utils.JsonUtils;
import com.dusty.boring.elastic.core.annotation.ElasticJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 *
 *    <提醒用户授信通过未借款任务>
 *
 * 需求描述：授信通过未借款
 * 任务时间：授信通过未借款的第48小时，以及第96小时
 * 模板名称：提现提醒
 *
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月03日 15:35
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月03日 15:35
 * </pre>
 */
@Slf4j
@ElasticJob(jobName = "ElasticJobAnnotationJobDemoScheduler",
        jobType = JobType.SIMPLE,
        corn = "0 0/6 * * * ?", //6分钟执行一次
        shardingTotalCount = 1,
        shardingItemParameters = "0=all")
@Component
public class ElasticJobAnnotationJobDemo implements SimpleJob {
    
    
    /**<pre>
     *  执行作业.
     *
     * @param shardingContext 分片上下文
     * </pre>
     */
    @Override
    public void execute(ShardingContext shardingContext) {
    
        log.info("\n-    开始执行作业：{},\n-    作业详情信息：\n-  {}",this.getClass().getCanonicalName(), JsonUtils.object2String(shardingContext));
        executeQuartzInternal();
    
        log.info("\n-   完成作业{}执行。", this.getClass().getCanonicalName());
    }
    
    /**
     * <pre>
     *     执行任务
     * </pre>
     */
    private void executeQuartzInternal() {
    }
}
