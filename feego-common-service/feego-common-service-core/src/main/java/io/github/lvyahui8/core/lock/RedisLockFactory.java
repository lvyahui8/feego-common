package io.github.lvyahui8.core.lock;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 23:01
 */
public class RedisLockFactory implements LockFactory {

    private final StringRedisTemplate redisTemplate;

    private final Long defaultTimeout;

    private final TimeUnit defaultTimeUnit;

    public RedisLockFactory(StringRedisTemplate redisTemplate, Long defaultTimeout, TimeUnit defaultTimeUnit) {
        this.redisTemplate = redisTemplate;
        this.defaultTimeout = defaultTimeout;
        this.defaultTimeUnit = defaultTimeUnit;
    }

    @Override
    public DistributedLock newDistributeLock(String lockKey,Object ns) {
        return new RedisDistributedLock(redisTemplate,lockKey,ns,defaultTimeout,defaultTimeUnit);
    }

}
