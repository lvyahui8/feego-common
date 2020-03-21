package io.github.lvyahui8.core.autoconfigure;

import io.github.lvyahui8.core.lock.LockFactory;
import io.github.lvyahui8.core.lock.RedisLockFactory;
import io.github.lvyahui8.core.properties.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 16:32
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class DistributeLockAutoConfiguration {
    @Autowired
    private ServiceProperties serviceProperties;

    @Bean
    public LockFactory lockFactory(@Qualifier("stringRedisTemplate") StringRedisTemplate stringRedisTemplate) {
        return new RedisLockFactory(stringRedisTemplate,
                serviceProperties.getDistributeLockTimeout(), TimeUnit.SECONDS);
    }
}
