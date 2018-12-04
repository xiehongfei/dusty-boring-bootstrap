package com.dusty.boring.elastic.core.autoconfigure;

import com.dusty.boring.common.annotation.MetaData;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <配合Bean方式注册ElasticJob作业的相关配置参数>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月01日 16:26
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月01日 16:26
 * </pre>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dusty.elasticjob")
public class ElasticJobProperties {
    
    private static Map<String, DustySimpleJob> cachedProperties;
    
    @MetaData(value = "是否启用bean加载elastic job", note = "默认false,为true时开启处理SimpleJob且未注解@ElasticJob的子类")
    private boolean enabledBeanMode = false;
    
    @MetaData(value = "simpleJob分片参数")
    private List<DustySimpleJob> dustySimpleJobs;
    
    @MetaData(value = "dataFlowJob分片参数")
    private List<DustyDataFlowJob> dustyDataFlowJobs;
    
    @MetaData(value = "scriptJob分片参数")
    private List<DustyScriptJob> dustyScriptJobs;
    
    /**
     * <pre>
     *     获取DustySimpleJobs配置
     *
     * @return 配置map
     * </pre>
     */
    public Map<String, DustySimpleJob> getCachedSimpleJobParams() {
        
        if (null == this.dustySimpleJobs)
            return Maps.newHashMap();
        
        if (null == cachedProperties) {
            cachedProperties = Maps.newHashMap();
            for (DustySimpleJob simpleJob : dustySimpleJobs) {
                cachedProperties.put(simpleJob.getJobClass(), simpleJob);
            }
        }
        
        return cachedProperties;
    }
    
    /**
     * <pre>
     *     SimpleJob分片参数
     * </pre>
     */
    @Getter
    @Setter
    public static class DustySimpleJob {
        
        @MetaData(value = "本地Class名称")
        private String jobClass;
        
        @MetaData(value = "corn表达式")
        private String corn;
    
        @MetaData(value = "分片总数")
        private int shardingTotalCount;
    
        @MetaData(value = "分片参数")
        private String shardingItemParameters;
        
    }
    
    /**
     * <pre>
     *     DataFlowJob
     * </pre>
     */
    @Getter
    @Setter
    public static class DustyDataFlowJob {
    
        @MetaData(value = "本地Class名称")
        private String jobClass;
        
        @MetaData(value = "corn表达式")
        private String corn;
    
        @MetaData(value = "分片总数")
        private int shardingTotalCount;
    
        @MetaData(value = "分片参数")
        private String shardingItemParameters;
    }
    
    /**
     * <pre>
     *     ScriptJob
     *     暂不实现
     * </pre>
     */
    @Getter
    @Setter
    public static class DustyScriptJob {
    
    }
    
    
}
