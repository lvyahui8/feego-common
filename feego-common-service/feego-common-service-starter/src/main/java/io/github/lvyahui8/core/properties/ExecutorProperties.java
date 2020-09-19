package io.github.lvyahui8.core.properties;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:17
 */
@Data
public class ExecutorProperties {
    /**
     * 是否初始化线程池
     */
    private boolean open                   = true;
    /**
     * 线程池核心线程数
     */
    private int     corePoolSize           = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 线程池最大线程数
     */
    private int     maxPoolSize            = Runtime.getRuntime().availableProcessors() * 7;
    /**
     * 非核心线程空闲存活时间， 单位为秒（s）
     */
    private int     keepAliveSeconds       = 60;
    /**
     * 任务等待队列容量
     */
    private int     queueCapacity          = Integer.MAX_VALUE;
    /**
     * 是否允许核心线程池也超时
     */
    private boolean allowCoreThreadTimeOut = false;
}
