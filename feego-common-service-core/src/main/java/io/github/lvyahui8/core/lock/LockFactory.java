package io.github.lvyahui8.core.lock;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:45
 */
public interface LockFactory {
    /**
     *
     * @param lockKey
     * @param ns
     * @return
     */
    DistributedLock newDistributeLock(String lockKey,Object ns) ;
}
