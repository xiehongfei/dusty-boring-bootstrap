/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 16:05
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.elastic.core.annotation;

import com.dangdang.ddframe.job.api.JobType;
import com.dusty.boring.common.annotation.MetaData;
import com.dusty.boring.common.constants.StringPool;

import java.lang.annotation.*;

/**
 * <pre>
 *
 *       <ElasticJob注解>
 *
 *           支持采用注解配置方式
 *            - 目前仅应用于支持ElasticJobLite
 *            - 目前仅处理了Class类注解（即：ElementType.Type）的应用。
 *              处理过程，可参考：{@link ElasticJobAnnotationProcessor}
 *
 *           参考：
 *           http://172.16.60.168/framework/backend/elastic-job/
 *           http://elasticjob.io/docs/elastic-job-lite/02-guide/config-manual/
 *           http://elasticjob.io/docs/elastic-job-lite/02-guide/job-sharding-strategy/
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 16:05
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月29日 16:05
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ElasticJob {
    
    @MetaData(value = "作业类型", note = "SIMPLE, CLOUD, SCRIPT")
    JobType jobType() default JobType.SIMPLE;
    
    @MetaData(value = "作业名称")
    String jobName();
    
    @MetaData(value = "corn表达式", note = "用于控制作业触发时间")
    String corn();
    
    @MetaData(value = "作业分片总数")
    int shardingTotalCount();
    
    @MetaData(value = "分片参数",
            note = "分片序列号和参数用等号分隔，多个键值对用逗号分隔分片序列号从0开始，不可大于或等于作业分片总数。如：0=a,1=b,2=c")
    String shardingItemParameters();
    
    @MetaData(value = "作业自定义参数", note = "可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业 例：每次获取的数据量、作业实例从数据库读取的主键等")
    String jobParameter() default "pageSize=20";
    
    @MetaData(value = "是否开启任务执行失效转移", note = "开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行")
    boolean failover() default false;
    
    @MetaData(value = "是否开启错过任务重新执行", note = "默认启用")
    boolean misfire() default true;
    
    @MetaData(value = "作业描述信息")
    String description() default "";
    
    @MetaData(value = "作业实现类", note = "需实现ElasticJob接口")
    String jobClass() default "";
    
    //~~ JobProperties 属性 BEGIN ~~
    @MetaData(value = "作业异常处理器", note = "应用于JobProperties")
    String jobExceptionHandler() default StringPool.DEFAULT_JOB_EXCEPTION_HANDLER_CLASS;
    
    @MetaData(value = "service处理器", note = "应用于JobProperties")
    String executorServiceHandler() default StringPool.DEFAULT_JOB_EXECUTOR_HANDLER_CLASS;
    //~~ JobProperties 属性 END   ~~
    
    //~~ LiteJobConfiguration 属性 BEGIN ~~
    @MetaData(value = "监控作业运行时状态", note = "每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。因为是瞬时状态，所以无必要监控。")
    boolean monitorExecution() default true;
    
    @MetaData(value = "最大允许的本机与注册中心的时间误差秒数", note = "配置为-1表示不校验时间误差")
    int maxTimeDiffSeconds() default -1;
    
    @MetaData(value = "作业监控端口", note = "建议配置作业监控端口, 方便开发者dump作业信息。使用方法: echo “dump” | nc 127.0.0.1 9888")
    int monitorPort() default -1;
    
    @MetaData(value = "作业分片策略实现类全路径", note = "默认使用平均分配策略,可参考：http://elasticjob.io/docs/elastic-job-lite/02-guide/job-sharding-strategy/")
    String jobShardingStrategyClass() default  StringPool.DEFAULT_JOB_STRATEGY_CLASS;
    
    @MetaData(value = "修复作业服务器不一致状态服务调度间隔时间", note = "配置为小于1的任意值表示不执行修复, 单位：分钟")
    int reconcileIntervalMinutes() default 10;
    
    @MetaData(value = "作业事件追踪的数据源Bean引用")
    String eventTraceRdbDataSource() default "";
    
    @MetaData(value = "设置作业是否启动时禁止", note = "可用于部署作业时, 先在启动时禁止, 部署结束后统一启动.")
    boolean disabled() default false;
    
    @MetaData(value = "设置本地配置是否可覆盖注册中心配置", note = "如果可覆盖, 每次启动作业都以本地配置为准.")
    boolean overwrite() default true;
    
    //~~ LiteJobConfiguration 属性 END   ~~
    
    //~~ listener params begin ~~
    @MetaData(value = "最后一个作业执行前的执行方法的超时时间。单位：毫秒", note = "默认值：Long.MAX_VALUE")
    long startedTimeoutMilliseconds() default Long.MAX_VALUE;
    
    @MetaData(value = "最后一个作业执行后的执行方法的超时时间。单位：毫秒", note = "默认值：Long.MAX_VALUE")
    long completedTimeoutMilliseconds()  default Long.MAX_VALUE;
    
    //~~ listener params end   ~~
    
    @MetaData(value = "注册中心服务Bean")
    String register() default "zookeeperRegistryCenter";
    
    @MetaData(value = "作业监听器", note = "作业普通监听器")
    String normalListener() default "";
    
    @MetaData(value = "作业监听器", note = "作业分布式监听器")
    String distributedOnceListener() default "";
    
}
