/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:36
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.cache;

import org.apache.commons.collections4.map.LRUMap;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DEFAULT_CACHE_INITIAL_SIZE;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DEFAULT_CACHE_MAX_SIZE;

/**
 * <pre>
 *
 *       <LRUCache>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月14日 14:36
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月14日 14:36
 * </pre>
 */
public class LocalLRUCache<K, V> extends LRUMap<K, V> {
    
    private static final long serialVersionUID = 3472667591792457809L;
    
    public LocalLRUCache() {
        this(DEFAULT_CACHE_MAX_SIZE);
    }
    
    public LocalLRUCache(int maxSize) {
        super(maxSize, DEFAULT_CACHE_INITIAL_SIZE);
    }
    
}
