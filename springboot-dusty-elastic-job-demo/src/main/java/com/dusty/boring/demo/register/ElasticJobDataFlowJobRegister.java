/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:30
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo.register;

import com.dangdang.ddframe.job.api.JobType;
import com.dusty.boring.elastic.core.annotation.ElasticJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <pre>
 *
 *       <注解+注册器的方式注入DataFlow Job>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月08日 11:30
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月08日 11:30
 * </pre>
 */
@ElasticJob(jobName = "ElasticJobOuterAnnotationDataFlowJobScheduler",
        corn = "0/50 * * * * ?",
        jobClass = "com.dusty.boring.demo.job.ElasticJobOuterAnnotationDataFlowJobDemo",
        shardingItemParameters = "0=alipay",
        shardingTotalCount = 1,
        jobType = JobType.DATAFLOW)
@Slf4j
@Component
public class ElasticJobDataFlowJobRegister {
    
    @PostConstruct
    public void init() {
        log.info("\n-\t\t Init Elastic Job Data Flow By @ElasticJob Annotation Beginning...");
    }
    
}
