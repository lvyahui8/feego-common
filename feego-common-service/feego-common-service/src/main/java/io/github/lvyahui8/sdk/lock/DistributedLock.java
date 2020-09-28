package io.github.lvyahui8.sdk.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:31
 */
public interface DistributedLock {

    /**
     * 阻塞式获取分布式锁
     */
    void lock() ;

    /**
     * 阻塞式获取分布式锁。 响应中断
     *
     * @throws InterruptedException  中断
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * 尝试获取锁， 获取不到立即返回
     * @return 是否拿到锁
     */
    boolean tryLock() ;

    /**
     * 尝试获取锁， 直到超时
     * @param timeout 超时时间
     * @param unit 超时时间单位
     * @return 是否拿到锁
     * @throws InterruptedException 被中断
     */
    boolean tryLock(Long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * 释放分布式锁
     */
    void unlock() ;

    /**
     * 修改锁的超时释放时间
     *
     * @param timeout 超时时间
     * @param timeUnit 超时时间单位
     */
    void setExpireTime(Long timeout, TimeUnit timeUnit);
}
