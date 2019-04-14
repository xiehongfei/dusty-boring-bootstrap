/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:50
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.cache;

import com.dusty.boring.mybatis.sql.common.annotation.MetaData;
import com.dusty.boring.mybatis.sql.common.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DEFAULT_CACHE_INITIAL_SIZE;

/**
 * <pre>
 *
 *       <先进先出Cache>
 *
 *           基于LinkedHashMap实现简易先进先出Map
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:50
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 14:50
 * </pre>
 */
public class LocalFifoCache<K, V> extends LinkedHashMap<K, V> {
    
    private static final long serialVersionUID = -1295794991639732609L;
    

//        @Getter
//        @Setter
//        @MetaData(value = "最后访问时间")
//        private long lastMills;
    
    @MetaData(value = "最大缓存容联")
    private int maxCacheCapacity;
    
    public LocalFifoCache() {
        this(DEFAULT_CACHE_INITIAL_SIZE);
    }
    
    public LocalFifoCache(int maxCacheCapacity) {
        
        super((int) Math.ceil(maxCacheCapacity /0.75) + 1, 1f, false);
        this.maxCacheCapacity = maxCacheCapacity;
    }
    
    /**
     * <pre>
     *     获取Key对应的Cache内容
     *     - 并记录最后访问时间
     *
     * @param key key
     * </pre>
     */
    @Override
    public V get(Object key) {
//            lastMills = System.currentTimeMillis();
        return super.get(key);
    }
    
    /**
     * {@inheritDoc}
     *
     * @param key
     * @param defaultValue
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
//            lastMills = System.currentTimeMillis();
        return super.getOrDefault(key, defaultValue);
    }
    
    /**
     *<pre>
     *     删除最年老的Entry条件
     *
     * @param eldest The least recently inserted entry in the map, or if
     *               this is an access-ordered map, the least recently accessed
     *               entry.  This is the entry that will be removed it this
     *               method returns <tt>true</tt>.  If the map was empty prior
     *               to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
     *               in this invocation, this will be the entry that was just
     *               inserted; in other words, if the map contains a single
     *               entry, the eldest entry is also the newest.
     *
     * @return <tt>true</tt> if the eldest entry should be removed
     * from the map; <tt>false</tt> if it should be retained.
     *</pre>
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > maxCacheCapacity;
    }
    
    /**
     * <pre>
     *     基于Md5算法及Base64算法构建LocalCacheKey
     *
     * @param  subKeys 子key
     * @return key 构建结果
     * </pre>
     */
    public static String buildLocalCacheKey(String... subKeys) {
        
        if (Objects.isNull(subKeys))
            return StringUtils.EMPTY;
        
        StringBuilder builder = new StringBuilder();
        for (String str : subKeys) {
            builder.append(str);
        }
        
        return EncryptUtils.base64(EncryptUtils.md5(builder.toString()));
    }
}
