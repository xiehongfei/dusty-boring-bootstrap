package com.dusty.boring.elastic.core.autoconfigure;

import com.dusty.boring.common.annotation.MetaData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 *       <Zookeeper Properties配置参数>
 *
 *           仅用于ElasticJob任务调度
 *
 *           参考：http://172.16.60.168/framework/backend/elastic-job/
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年11月29日 16:31
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年11月29日 16:31
 * </pre>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dusty.elasticjob.zk")
public class ZookeeperProperties {

    @MetaData(value = "连接Zookeeper服务器的列表", note = "包括IP地址和端口号.多个地址用逗号分隔.如: host1:2181,host2:2181")
    private String servers;
    
    @MetaData(value = "elasticjob在zk注册节点的命名空间")
    private String namespace;
    
    @MetaData(value = "基础休眠时间")
    private int baseSleepTimeMilliseconds = 1000;
    
    @MetaData(value = "最大休眠时间")
    private int maxSleepTimeMilliseconds = 3000;
    
    @MetaData(value = "重试次数", note = "默认3次")
    private int maxRetries = 3;
    
    @MetaData(value = "session超时时间")
    private int sessionTimeoutMilliseconds = 60000;
    
    @MetaData(value = "连接超时时间")
    private int connectionTimeoutMilliseconds = 15000;
    
    @MetaData(value = "连接Zookeeper的权限令牌", note = "缺省为不需要权限验证")
    private String digest;
    
    //~~~ 增加属性 BEGIN ~~~
    @MetaData(value = "锁定周期单位")
    private TimeUnit lockPeriodTimeUnit;
    
    @MetaData(value = "锁定周期时长")
    private long lockPeriodTime;
    //~~~ 增加属性 END ~~~
    
}
