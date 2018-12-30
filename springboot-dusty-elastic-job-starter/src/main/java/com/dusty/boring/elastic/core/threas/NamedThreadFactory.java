package com.dusty.boring.elastic.core.threas;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *
 *       <NamedThreadFactory>
 *
 *           解决{@link java.util.concurrent.ThreadPoolExecutor}构造时，
 *           @see Executors#defaultThreadFactory() 不能构造指定线程前缀的简易方案。
 *
 *           目前主要应用于{@link CommonAsyncConfig#getAsyncExecutor()} 及 {@link MqAsyncPoolConfig#getAsyncExecutor()}
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年12月27日 10:10
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2018年12月27日 10:10
 * </pre>
 */
public class NamedThreadFactory implements ThreadFactory {
    
    private final String namePrefix; //线程前缀
    private final ThreadGroup group; //线程组
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    
    
    public NamedThreadFactory(String threadPrefix) {
    
        threadPrefix = StringUtils.isEmpty(threadPrefix) ? "-" : threadPrefix + "-";
        
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = threadPrefix + poolNumber.getAndIncrement() + "-thread-";
    }
    
    /**
     * <pre>
     *     构建新线程
     *
     * @param r runnable
     * @return 线程
     * </pre>
     */
    public Thread newThread(Runnable r) {
        
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        
        if (t.isDaemon())
            t.setDaemon(false);
        
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        
        return t;
    }
    
}
