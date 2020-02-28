package io.github.lvyahui8.core.lock;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:32
 */
public class RedisDistributedLock implements DistributedLock {

    private StringRedisTemplate redisTemplate;

    private String key;

    private String ns;

    private Long expireTime;

    private TimeUnit timeUnit;

    private static final byte [] CAD_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end".getBytes();

    RedisDistributedLock(StringRedisTemplate redisTemplate, String key, Object ns,
                         Long timeout, TimeUnit timeUnit) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.ns = ns.toString();
        this.expireTime = timeout;
        this.timeUnit = timeUnit;
    }


    @Override
    public void lock() {
        while(true) {
            if (tryLock()) {
                return;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        while(true) {
            if (tryLock()) {
                return;
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) {
        long nanoTime = System.nanoTime();
        while(System.nanoTime() - nanoTime > unit.toNanos(timeout)) {
            if (tryLock()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryLock() {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, ns, expireTime, timeUnit);
        return res != null && res;
    }

    @Override
    public void unlock() {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[] keyBytes = key.getBytes();
            byte[] valueBytes = ns.getBytes();
            return  connection.eval(CAD_LUA, ReturnType.INTEGER,1,keyBytes,valueBytes);
        });
    }

    @Override
    public void setExpireTime(long timeout, TimeUnit timeUnit) {
        this.expireTime = timeout;
        this.timeUnit = timeUnit;
    }
}
