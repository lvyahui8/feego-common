package io.github.lvyahui8.sdk.reddot;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/22
 */
public class DefaultRedDotManager implements RedDotManager {

    public StringRedisTemplate redisTemplate;

    public DefaultRedDotManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void enable(Object key, RedDot... redDots) {

    }

    @Override
    public boolean isActive(Object key, RedDot redDot) {
        return false;
    }

    @Override
    public Map<RedDot, Boolean> isActiveMap(Object key, RedDot... redDots) {
        return null;
    }
}
