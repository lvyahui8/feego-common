package io.github.lvyahui8.sdk.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:31
 */
public interface DistributedLock extends Lock {

    /**
     * 修改锁的超时释放时间
     *
     * @param timeout 超时时间
     * @param timeUnit 超时时间单位
     */
    void setExpireTime(long timeout, TimeUnit timeUnit);
}
