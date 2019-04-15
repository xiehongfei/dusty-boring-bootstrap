/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 13:58
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.common.cache;

import com.alibaba.druid.support.json.JSONUtils;
import junit.framework.TestCase;

/**
 * <pre>
 *
 *       <LocalLRUCache>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月15日 13:58
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月15日 13:58
 * </pre>
 */
public class LocalLRUCacheTest extends TestCase {
    
    
    public void test() {
        
        LocalLRUCache localLRUCache = new LocalLRUCache(3);
        
        localLRUCache.put("1", "zhangsan");
        localLRUCache.put("2", "lisi");
        localLRUCache.put("3", "wangwu");
        
        System.out.println("---------------------");
        System.out.println(JSONUtils.toJSONString(localLRUCache));
        localLRUCache.get("2");
        localLRUCache.get("1");
        
        System.out.println("---------------------");
        System.out.println(JSONUtils.toJSONString(localLRUCache));
        localLRUCache.put("4", "maliu");
        
        System.out.println("---------------------");
        System.out.println(JSONUtils.toJSONString(localLRUCache));
        
    }
    
}
