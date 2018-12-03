# dusty-boring-bootstrap
elastic job with springboot 

##目录
- [1.简介](#1.简介)
- [2.功能列表](#2.功能列表：)
- [3.使用说明](#3.使用说明)
  - [3.1.基于JavaBean的使用方法](#--3.1.基于javabean的使用方法)
    - [3.1.1.继承SimpleJob接口](#3.1.1.继承simplejob接口；)
    - [3.1.2.配置文件启用JavaBean方式](#3.1.2.配置文件启用javabean方式)
    - [3.1.3.配置完成](#3.1.3.集成完成（done）。)
  - [3.2.基于注解的使用方法](#--3.2.基于注解的使用方法)
    - [3.2.1.配置文件application.yml](#3.2.1.配置文件application.yml)
    - [3.2.2.任务类添加@Elastic注解](#3.2.2.任务类添加@elastic注解)
    - [3.2.3.完成集成（DONE）](#3.2.3.完成集成（done）。)


## 1.简介
  集成当当网Elastic Job定时任务分布式调度解决方案，
  基于SpringBoot项目风格快速集成，拆箱即用，简单高效。

## 2.功能列表：
 - 支持JavaBean加yml文件方式自动注册ElasticJob任务；
 - 支持基于注解@ElasticJob方式自动注册ElasticJob任务；
 - 代码结构中的Apache Curator 暂未完全集成，不影响1.2功能使用；

## 3.使用说明
#### - 3.1.基于JavaBean的使用方法
> 3.1.1.继承SimpleJob接口；
```java
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
```

> 3.1.2.配置文件启用JavaBean方式
```yaml
dusty:
  elasticjob:
    enabled-bean-mode: true #启用Bean作业解析
    zk:
      servers: 172.16.151.33:2181,172.16.151.34:2181,172.16.151.35:2181
      namespace: ${spring.application.name}
    sxp-simple-jobs:
      - job-class: ElasticSimpleJob
        corn: 0/30 * * * * ?
        shardingTotalCount: 1
        shardingItemParameters: 1=wechat  
```
###### 3.1.3.集成完成（DONE）。

#### - 3.2.基于注解的使用方法
> 3.2.1.配置文件application.yml
```yaml
#~~ Elastic Job 配置BEGIN ~~
dusty:
  elasticjob:
    enabled-bean-mode: false #禁用Bean作业解析
    zk:
      servers: 172.16.151.33:2181,172.16.151.34:2181,172.16.151.35:2181
      namespace: ${spring.application.name}
    sxp-simple-jobs:
      - job-class: ElasticSimpleJob
        corn: 0/30 * * * * ?
        shardingTotalCount: 1
        shardingItemParameters: 1=wechat
#~~ Elastic Job 配置END ~~
```
> 3.2.2.任务类添加@Elastic注解
```java
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
```
> 3.2.3.完成集成（DONE）。


