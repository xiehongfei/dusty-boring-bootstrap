/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:14
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dusty.boring.common.utils.JsonUtils;
import com.dusty.boring.demo.beans.Foo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * <pre>
 *
 *       <DataFlowJob 实现类>
 *           通过@Elastic注解，
 *           交由{@link com.dusty.boring.demo.register.ElasticJobDataFlowJobRegister}
 *           注册本作业任务
 *
 *           DataFlow类型Job，采用流式处理机制，fetchData不返回空结果将持续执行作业。
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:14
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月08日 11:14
 * </pre>
 */
@Slf4j
@Component
public class ElasticJobOuterAnnotationDataFlowJobDemo implements DataflowJob<Foo> {
    
    private ThreadLocal<Integer> LOCAL = ThreadLocal.withInitial(()-> 0);
    
    private ThreadLocal<String> JOB_NAME = ThreadLocal.withInitial(()-> String.format("outer-job-%s", UUID.randomUUID().toString()));
    
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
        
            log.info("-\n\t {}-任务结束。", JOB_NAME.get());
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
