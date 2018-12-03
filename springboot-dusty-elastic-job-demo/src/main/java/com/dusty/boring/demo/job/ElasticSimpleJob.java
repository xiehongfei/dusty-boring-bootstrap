package com.dusty.boring.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dusty.boring.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * <ElasticSimpleJob Bean方式注册Elastic Lite Job>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 17:11
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 17:11
 *   {
 *        "jobName": "ElasticSimpleJobScheduler",
 *        "jobClass": "com.dusty.boring.demo.job.ElasticSimpleJobScheduler",
 *        "jobType": "SIMPLE",
 *        "cron": "0/5 * * * * ?",
 *        "shardingTotalCount": 1,
 *        "shardingItemParameters": "1=wechat",
 *        "jobParameter": "",
 *        "failover": false,
 *        "misfire": true,
 *        "description": "",
 *        "jobProperties": {
 *            "job_exception_handler": "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler",
 *            "executor_service_handler": "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler"
 *        },
 *        "monitorExecution": true,
 *        "maxTimeDiffSeconds": -1,
 *        "monitorPort": -1,
 *        "jobShardingStrategyClass": "",
 *        "reconcileIntervalMinutes": 10,
 *        "disabled": false,
 *        "overwrite": false
 *    }
 * </pre>
 */
@Slf4j
@Component
public class ElasticSimpleJob implements SimpleJob {
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        
        log.info(
                 "\n-    开始执行定时任务:{}" +
                 "\n-    任务详情：{}",
                shardingContext.getJobName(), JsonUtils.object2String(shardingContext));
    }
}
