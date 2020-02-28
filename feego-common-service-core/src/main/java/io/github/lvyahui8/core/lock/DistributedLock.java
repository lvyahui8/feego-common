package io.github.lvyahui8.core.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:31
 */
public interface DistributedLock {

    void lock() ;

    void lockInterruptibly() throws InterruptedException;

    boolean tryLock() ;

    boolean tryLock(long timeout, TimeUnit unit);

    void unlock() ;

    void setExpireTime(long timeout, TimeUnit timeUnit);
}
