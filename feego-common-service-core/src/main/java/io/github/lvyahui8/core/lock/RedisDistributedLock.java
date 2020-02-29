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
            try {
                if (tryLock()) {
                    return;
                }
            } catch (Exception e) {
                /* 忽略中断异常 */
                if (searchInterruptedException(e) == null) {
                    throw e;
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        tryLock(null,null);
    }

    @Override
    public boolean tryLock(Long timeout, TimeUnit unit) throws InterruptedException {
        long beginTime = System.nanoTime();
        while(true) {
            if (timeout != null && unit != null && System.nanoTime() - beginTime > unit.toNanos(timeout)) {
                return false;
            }
            try {
                if (tryLock()) {
                    return true;
                }
            } catch (Exception e) {
                InterruptedException interruptedException = searchInterruptedException(e);
                if (interruptedException != null) {
                    /* 因中断引起的异常单独抛出中断异常 */
                    throw interruptedException;
                } else {
                    throw e;
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }

    @Override
    public boolean tryLock() {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, ns, expireTime, timeUnit);
        return res != null && res;
    }

    @Override
    public void unlock() {
        try {
            redisTemplate.execute((RedisCallback<Long>) connection -> {
                byte[] keyBytes = key.getBytes();
                byte[] valueBytes = ns.getBytes();
                return  connection.eval(CAD_LUA, ReturnType.INTEGER,1,keyBytes,valueBytes);
            });
        } catch (Exception e) {
            if (searchInterruptedException(e) == null) {
                throw e;
            }
        }
    }

    private InterruptedException searchInterruptedException(Exception e) {
        Throwable eNode = e;
        InterruptedException interruptedException = null;
        do {
            if (eNode instanceof InterruptedException) {
                interruptedException = (InterruptedException) eNode;
                break;
            }
        } while ((eNode = eNode.getCause()) != null);
        return interruptedException;
    }

    @Override
    public void setExpireTime(Long timeout, TimeUnit timeUnit) {
        this.expireTime = timeout;
        this.timeUnit = timeUnit;
    }
}
