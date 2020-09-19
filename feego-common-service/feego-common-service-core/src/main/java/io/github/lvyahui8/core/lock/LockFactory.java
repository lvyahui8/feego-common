package io.github.lvyahui8.core.lock;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:45
 */
public interface LockFactory {
    /**
     * 获取分布式锁实例
     * - 此分布式锁并没有可重入特性。
     * - 为保证进程crash后， 占用的分布式锁释放掉， 锁必须超时失效
     * - 业务代码需要保证执行时间不超过锁的失效时间
     *
     * @param lockKey 锁的唯一标识
     * @param ns  每次为锁分配不同的值，以保证不会unlock掉别人的锁。如果没有很好的选择， 可以使用UUID
     * @return 锁实例
     */
    DistributedLock newDistributeLock(String lockKey,Object ns) ;
}
