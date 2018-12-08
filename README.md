# dusty-boring-bootstrap
elastic job with springboot 

## 目录
- [1.简介](#1.简介)
- [2.功能列表](#2.功能列表：)
- [3.使用说明](#3.使用说明（simplejob）)
  - [3.1.基于JavaBean的使用方法](#3.1.基于javabean的使用方法)
    - [3.1.1.继承SimpleJob接口](#3.1.1.继承simplejob接口；)
    - [3.1.2.配置文件启用JavaBean方式](#3.1.2.配置文件启用javabean方式)
    - [3.1.3.配置完成](#3.1.3.集成完成（done）)
  - [3.2.基于注解的使用方法](#3.2.基于注解的使用方法)
    - [3.2.1.配置文件application.yml](#3.2.1.配置文件application.yml)
    - [3.2.2.任务类添加@Elastic注解](#3.2.2.任务类添加@elastic注解)
    - [3.2.3.完成集成（DONE）](#3.2.3.完成集成（done）。)
- [4.使用说明（dataflow类型Job）](#4.使用说明（dataflow类型job）)
    - [4.1.基于@elastic注解使用](#4.1.基于@elastic注解使用)
    - [4.2.基于register联合@elasticjob注解方式](#4.2.基于register联合@elasticjob注解方式)
    - [4.3. Done](#4.3.-集成完成)
- [5.运维平台](#5.运维平台)
  - [5.1.运维平台部署](#5.1.运维平台部署)
  - [5.2.运维平台配置](#5.2.运维平台配置)
  

## 1.简介
  集成当当网Elastic Job定时任务分布式调度解决方案，
  基于SpringBoot项目风格快速集成，拆箱即用，简单高效。

## 2.功能列表：
 - 支持JavaBean加yml文件方式自动注册ElasticJob任务；
 - 支持基于注解@ElasticJob方式自动注册ElasticJob任务；
 - 代码结构中的Apache Curator 暂未完全集成，不影响1.2功能使用；

## 3.使用说明（SimpleJob）
#### 3.1.基于JavaBean的使用方法
###### 3.1.1.继承SimpleJob接口；
> 代码示例
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

###### 3.1.2.配置文件启用JavaBean方式
> 代码示例
>
```yaml
dusty:
  elasticjob:
    enabled-bean-mode: true #启用Bean作业解析
    zk:
      servers: localhost:2181 #如多个注册中心，可localhost:2181,host1:2181,host2:2181
      namespace: ${spring.application.name}
    dusty-simple-jobs:
      - job-class: ElasticSimpleJob
        corn: 0/30 * * * * ?
        shardingTotalCount: 1
        shardingItemParameters: 1=wechat  
```
###### 3.1.3.集成完成（DONE）

#### 3.2.基于注解的使用方法
###### 3.2.1.配置文件application.yml
```yaml
#~~ Elastic Job 配置BEGIN ~~
dusty:
  elasticjob:
    enabled-bean-mode: false #禁用Bean作业解析
    zk:
      servers: localhost:2181 #如多个注册中心，可localhost:2181,host1:2181,host2:2181
      namespace: ${spring.application.name}
    dusty-simple-jobs:
      - job-class: ElasticSimpleJob
        corn: 0/30 * * * * ?
        shardingTotalCount: 1
        shardingItemParameters: 1=wechat
#~~ Elastic Job 配置END ~~
```
###### 3.2.2.任务类添加@Elastic注解
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
###### 3.2.3.完成集成（DONE）。

## 4.使用说明（DataFlow类型Job）

#### 4.1.基于@Elastic注解使用
> 优点：实现简单
>
> 缺点：职责结构耦合度偏高

###### 4.1.1.注解@Elastic且jobType指定为JobType.DATAFLOW类型
>
###### 4.1.2.集成DataflowJob接口，实现fetchData与processData方法
>> 代码示例如下：
```java
/**
 * <pre>
 *
 *       <直接由注解@ElasticJob注解注册DataFlowJob>
 *
 *           DataFlow类型Job，采用流式处理机制，fetchData不返回空结果将持续执行作业。
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:35
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月08日 11:35
 * </pre>
 */
@ElasticJob(jobName = "ElasticJobInnerAnnotationDataFlowJobScheduler",
        corn = "0/30 * * * * ?",
        shardingItemParameters = "0=alipay",
        shardingTotalCount = 1,
        jobType = JobType.DATAFLOW)
@Slf4j
@Component
public class ElasticJobInnerAnnotationDataFlowJobDemo implements DataflowJob<Foo> {
    
    private ThreadLocal<Integer> LOCAL = ThreadLocal.withInitial(()-> 0);
    
    private ThreadLocal<String> JOB_NAME = ThreadLocal.withInitial(()-> String.format("inner-job-%s", UUID.randomUUID().toString()));
    
    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     *
     * @return 待处理的数据集合
     */
    @Override
    public List<Foo> fetchData(ShardingContext shardingContext) {
        
        if (LOCAL.get() > 3) {
            
            log.info("\n-\t {}-任务结束。", JOB_NAME.get());
            //重置LOCAL=0,为下次任务配置执行条件。
            LOCAL.set(0);
            return null;
        }
        
        List<Foo> foos = Lists.newArrayList();
        
        for (int i = 0; i < 10; i ++){
            foos.add(new Foo());
        }
        LOCAL.set(LOCAL.get() + 1);
        
        return foos;
    }
    
    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data            待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<Foo> data) {
        log.info("\n-\t\t {}-开始处理数据:", JOB_NAME.get());
        data.forEach(item-> log.info("\n\t\t {}\t{}", JOB_NAME.get(), JsonUtils.object2String(item)));
    }
}
```

#### 4.2.基于Register联合@ElasticJob注解方式

> 优点：结构清晰
>
> 缺点：实现复杂度稍高

###### 4.2.1.作业类继承DataflowJob接口，并分别实现fetchData及processData方法；
>
###### 4.2.2.编写Register类，注解@ElasticJob, 并指定jobClass为4.2.1的Job类全限定名；
>
> 代码示例
>
``参加springboot-dusty-elastic-job-demo -> 
ElasticJobDataFlowJobRegister 及  ElasticJobOuterAnnotationDataFlowJobDemo``

#### 4.3. 集成完成


## 5.运维平台

#### 5.1.运维平台部署
> 访问 elastic job官网 [运维平台部署指南](http://elasticjob.io/docs/elastic-job-lite/01-start/deploy-guide/)，可通过github.com下载elastic-lite-console本地部署。

#### 5.2.运维平台配置

> 5.2.1.配置注册中心

![elastic-lite-console 注册中心配置](https://raw.githubusercontent.com/xiehongfei/dusty-boring-bootstrap/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/elasticjob-console%E9%85%8D%E7%BD%AE%E4%B8%AD%E5%BF%83.png)


![elastic-lite-console 注册中心列表](https://raw.githubusercontent.com/xiehongfei/dusty-boring-bootstrap/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/elasticjob-console%E9%85%8D%E7%BD%AE%E4%B8%AD%E5%BF%83-%E9%85%8D%E7%BD%AE%E6%88%90%E5%8A%9F.png)


###### 5.2.2.查询任务（作业）信息

![elastic-lite-console 任务列表](https://raw.githubusercontent.com/xiehongfei/dusty-boring-bootstrap/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/elasticjob-console%E5%8F%B0%E4%BB%BB%E5%8A%A1%E7%8A%B6%E6%80%81.png)


![elastic-lite-console 任务详情](https://raw.githubusercontent.com/xiehongfei/dusty-boring-bootstrap/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/elasticjob-%E4%BD%9C%E4%B8%9A%E8%AF%A6%E6%83%85.png)


> DataFlow Job 补充效果图

![elastic-lite-console 任务列表](https://github.com/xiehongfei/dusty-boring-bootstrap/blob/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/elasticjob-console-dataflowjob.png)

![elastic-lite-data-flow-job-result](https://github.com/xiehongfei/dusty-boring-bootstrap/blob/master/springboot-dusty-elastic-job-demo/src/main/resources/statics/images/%E6%89%A7%E8%A1%8Cdataflow-job%E7%BB%93%E6%9E%9C.png)