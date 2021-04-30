package io.github.lvyahui8.example.service;

import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.configuration.CacheKey;
import io.github.lvyahui8.sdk.cache.AsyncRefreshableCacheObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/20
 */
@Component
public class UserCacheObject extends AsyncRefreshableCacheObject<Long, UserDTO> {

    final UserService userService;

    public UserCacheObject(StringRedisTemplate redisTemplate, UserService userService) {
        super(redisTemplate);
        this.userService = userService;
    }

    @Override
    protected UserDTO syncLoad(Long userId) {
        return userService.getUser(userId);
    }

    @Override
    protected boolean preventBreakdown() {
        return true;
    }

    @Override
    protected String getRedisKey(Long userId) {
        return CacheKey.user_profile.getKey() + userId;
    }

    @Override
    protected int getLogicTimeoutSecond() {
        return 1;
    }
}
