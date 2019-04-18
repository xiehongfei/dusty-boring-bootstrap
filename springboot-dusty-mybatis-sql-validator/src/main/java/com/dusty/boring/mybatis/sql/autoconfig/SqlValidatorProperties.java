/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:35
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.autoconfig;

import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.context.SpringContextHolder;
import com.google.common.collect.Lists;
import com.google.common.primitives.Booleans;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.ZERO;

/**
 * <pre>
 *
 *       <Sql Validator Properties>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月13日 21:35
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月13日 21:35
 * </pre>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dusty.validator.sql")
public class SqlValidatorProperties {
    
    @MetaData(value = "环境标签项")
    private EnvProfiles envProfiles;
    
//    @MetaData(value = "检查项配置")
//    private ValidateItems validateItems;
    
    @MetaData(value = "检查项配置")
    private MySqlValidateItems mySqlValidItems;
    
    @MetaData(value = "告警执行SQL时长", note = "超过warnWhenMills即告警，默认512毫秒")
    private long warnOverMills = 2 << 8;
    
    @MetaData(value = "错误执行SQL时长", note = "超过errorOverMills即报错，默认Long.MAX_VALUE毫秒")
    private long errorOverMills = 0x7fffffffffffffffL;
    
    @Getter
    @Setter
    public static class EnvProfiles implements Serializable {
    
        private static final long serialVersionUID = -4461417917745401470L;
        
        @MetaData(value = "需要检查环境")
        private List<String> needCheckEnvs = Lists.newArrayList(Arrays.asList("dev", "test", "rc"));
        
        @MetaData(value = "跳过检查的环境")
        private List<String> ignoreCheckEnvs = Lists.newArrayList(Collections.singletonList("prod"));
        
        @MetaData(value = "白名单启用环境")
        private List<String> enableWhiteListCacheEnvs = Lists.newArrayList(Arrays.asList("dev", "test", "rc"));
        
        @MetaData(value = "黑名单启用环境")
        private List<String> enableBlackListCacheEnvs = Lists.newArrayList(Arrays.asList("dev", "test", "rc"));
        
        public boolean enableBlackListCache() {
            
            if (Objects.isNull(enableBlackListCacheEnvs) || CollectionUtils.isEmpty(enableBlackListCacheEnvs)) {
                return false;
            }
            
            return enableBlackListCacheEnvs.contains(SpringContextHolder.getEnvironment());
        }
    }
    
    @Getter
    @Setter
    @MetaData(value = "检查项目")
    public static class ValidateItems implements Serializable {
    
        private static final long serialVersionUID = 4806471600092005124L;
    
        @MetaData(value = "是否启用DDL语句Drop*命令", note = "默认：关闭")
        private boolean enableDdlDrop                                    = false;
        
        @MetaData(value = "是否允许comment语句", note = "默认：关闭")
        private boolean enableDdlComment                                 = false;
        
        @MetaData(value = "是否允许使用IN语句", note = "默认：关闭")
        private boolean enableCondIn                                     = true;
    
        @MetaData(value = "是否启用OR语句", note = "默认：关闭")
        private boolean enableCondOr                                     = true;
    
        @MetaData(value = "是否启用NotEqual语句", note = "默认：开启")
        private boolean enableCondNE                                     = false;
    
        @MetaData(value = "是否启用Where条件检查", note = "默认：开启")
        private boolean enableWhereCheck                                 = true;
    
        @MetaData(value = "是否启用LIKE检查", note = "默认：开启")
        private boolean enableLikeCond                                   = true;
    
        @MetaData(value = "是否启用索引检查", note = "默认：关闭")
        private boolean mustUseIndexCheck                                = false;
        
        @MetaData(value = "是否允许explain语句", note = "默认：开启")
        private boolean enableExplain                                    = true;
        
        @MetaData(value = "是否允许sql优化语句", note = "默认：开启")
        private boolean sqlOptimize                                      = true;
    }
    
    @Getter
    @Setter
    @MetaData(value = "MySql检查项")
    public static class MySqlValidateItems extends ValidateItems {
    
        private static final long serialVersionUID = -8320378595257516228L;
        
        @MetaData(value = "是否允许LockTable命令", note = "默认：关闭")
        private boolean enableLockTable = false;
        
        public boolean anyItemsTrue() {
    
            return ZERO.equals(Booleans.countTrue(
                    isEnableCondIn(), isEnableCondOr(),
                    isEnableLikeCond(), isEnableCondNE(),
                    isEnableWhereCheck(), isMustUseIndexCheck()));
        }
        
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
