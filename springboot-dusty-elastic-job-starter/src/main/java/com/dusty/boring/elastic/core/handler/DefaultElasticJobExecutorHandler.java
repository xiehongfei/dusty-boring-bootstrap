package com.dusty.boring.elastic.core.handler;

import com.dangdang.ddframe.job.executor.handler.ExecutorServiceHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.suixingpay.stageloan.marketing.common.support.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * <ElasticJob处理线程池>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 23:49
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 23:49
 * </pre>
 */
public class DefaultElasticJobExecutorHandler implements ExecutorServiceHandler {
  
    /**
     * 创建线程池服务对象.
     *
     * @param jobName 作业名
     *
     * @return 线程池服务对象
     */
    @Override
    public ExecutorService createExecutorService(String jobName) {
    
        //~~ 特别说明 BEGIN ~~
        //为避免BlockingDeque过大，造成系统内存爆满，使用new ThreadPoolExecutor方式构造线程池
        //禁止使用Executors类之相关静态方法构造线程池
        //~~ 特别说明 END   ~~
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setNameFormat("elastic-job-executor-pool");
        builder.setUncaughtExceptionHandler(new ElasticJobAsyncUncaughtExceptionHandler());
        builder.setPriority(Thread.NORM_PRIORITY);
        
        return new ThreadPoolExecutor(ThreadUtils.getRunThreads() * 2 + 1,
                ThreadUtils.getRunThreads() * 5,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(400),
                builder.build());
        
    }
    
    /**
     * <pre>
     *     Async异步方法异常处理器
     *
     *     捕获异步线程未能抛出的运行时异常
     * </pre>
     */
    @Slf4j
    public static class ElasticJobAsyncUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        
       
        /**
         * Method invoked when the given thread terminates due to the
         * given uncaught exception.
         * <p>Any exception thrown by this method will be ignored by the
         * Java Virtual Machine.
         *
         * @param t the thread
         * @param e the exception
         */
        @Override
        public void uncaughtException(Thread t, Throwable e) {
    
            log.error("\n*********STAGELOAN异步执行器，处理任务异常。*********"
                    + "\n*********异常线程：name={},id={}"
                    + "\n*********异常信息：{} "
                    + "\n {}", t.getName(), t.getId(), e.getMessage(), e.getCause());
        }
    }
}
