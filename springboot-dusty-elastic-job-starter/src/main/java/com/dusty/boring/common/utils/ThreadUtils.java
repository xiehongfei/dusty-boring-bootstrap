package com.dusty.boring.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <线程工具类>
 *
 * @author: xiehongfei[humphreytes@gmail.com]
 * @date: 2018年08月07日 17:59
 * @version: V1.0
 * @review: xiehongfei[humphreytes@gmail.com]/2018年08月07日 17:59
 * </pre>
 */
public class ThreadUtils {

    private static final Logger log = LoggerFactory.getLogger(ThreadUtils.class);
    
    
    /**
     * <pre>
     *     可用处理器数
     *
     * @return threadCnt 可用处理器数量
     * </pre>
     */
    public static int getRunThreads() {
        
        return Runtime.getRuntime().availableProcessors();
    }
    
    /**
     * <pre>
     *     监控当前JVM空闲内存
     *
     * @return long 空闲大小
     * </pre>
     */
    public static long monitorFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }
    
    /**
     * <pre>
     *     监控当前JVM总内存
     *
     * @return long 内存大小
     * </pre>
     */
    public static long monitorTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
    
    /**
     * <pre>
     *     线程休眠毫秒
     *
     * @param millis 毫秒
     * </pre>
     */
    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("****线程休眠异常", e);
        }
    }

    /**
     * <pre>
     *     线程休眠x秒
     *
     * @param seconds 秒
     * </pre>
     */
    public static void sleepSeconds(long seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error("****线程休眠异常", e);
        }
    }

    /**
     * <pre>
     *     线程休眠x分
     *
     * @param mins 分钟
     * </pre>
     */
    public static void sleepMins(int mins) {
        try {
            Thread.sleep(mins * 60 * 1000);
        } catch (InterruptedException e) {
            log.error("****线程休眠异常", e);
        }

    }

}
