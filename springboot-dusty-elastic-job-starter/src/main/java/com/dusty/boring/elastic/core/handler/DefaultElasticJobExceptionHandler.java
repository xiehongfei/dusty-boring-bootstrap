package com.dusty.boring.elastic.core.handler;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <作业异常处理器>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年11月30日 09:51
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年11月30日 09:51
 * </pre>
 */
@Slf4j
public class DefaultElasticJobExceptionHandler implements JobExceptionHandler {
    

    /**
     * 处理作业异常.
     *
     * @param jobName 作业名称
     * @param cause   异常原因
     */
    @Override
    public void handleException(String jobName, Throwable cause) {
        log.error("**** ElasticJob 发生异常，Job名称={}，异常信息：{}", jobName, cause.getMessage());
    }
}
