package com.dusty.boring.curator.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * <Curator Zookeeper Client>
 *
 *     参考链接：
 *     https://github.com/apache/curator/tree/master/curator-examples/src/main/java
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年12月02日 21:16
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年12月02日 21:16
 * </pre>
 */
public class DustyCuratorZookClient  {

    private static final String ZK_ADDR = "localhost:2181";
    private static final String ZK_PATH = "/dusty-zk-curator-test";
    
    private CuratorFramework client;
    
    public DustyCuratorZookClient() {
        
        client =
                CuratorFrameworkFactory.newClient(ZK_ADDR,
                        6000,
                        5000,
                        new RetryNTimes(5, 5000));
    
        client.start();
    }
    
    
    public void doTest() throws Exception {
        String data = "hello";
        client.create().creatingParentsIfNeeded().forPath(ZK_PATH, data.getBytes(StandardCharsets.UTF_8));
        //client.getChildren();
    }
    

}
