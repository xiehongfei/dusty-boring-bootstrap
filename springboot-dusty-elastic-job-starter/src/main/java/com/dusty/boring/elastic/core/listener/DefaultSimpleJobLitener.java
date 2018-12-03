package com.dusty.boring.elastic.core.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dusty.boring.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <默认SimpleJob监听器>
 *     可通过分析作业执行时长，动态调整是否开启本监听
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 17:05
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 17:05
 * </pre>
 */
@Slf4j
public class DefaultSimpleJobLitener implements ElasticJobListener {
    
    /**
     * <pre>
     *     作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     * </pre>
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info(
                "\n-    开始执行定时任务:{}" +
                "\n-    任务详情:\n-m  {}",
                shardingContexts.getJobName(), JsonUtils.object2String(shardingContexts));
    }
    
    /**
     * <pre>
     *     作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     * </pre>
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info(
                 "\n-    结束执行定时任务:{}" +
                 "\n-    任务详情:" +
                 "\n-  {}",
                shardingContexts.getJobName(), shardingContexts);
    }
}
