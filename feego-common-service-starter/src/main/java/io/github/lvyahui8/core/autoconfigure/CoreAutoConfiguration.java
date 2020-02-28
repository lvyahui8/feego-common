package io.github.lvyahui8.core.autoconfigure;


import io.github.lvyahui8.core.constants.Constant;
import io.github.lvyahui8.core.lock.LockFactory;
import io.github.lvyahui8.core.lock.RedisLockFactory;
import io.github.lvyahui8.core.properties.ExecutorProperties;
import io.github.lvyahui8.core.properties.ServiceProperties;
import io.github.lvyahui8.core.utils.AsyncTaskExecutorInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:00
 */
@Configuration
@EnableConfigurationProperties({ServiceProperties.class})
public class CoreAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ServiceProperties serviceProperties;

    @Bean
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX ,name =  "executor.open",matchIfMissing = true)
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        ExecutorProperties executorProperties = serviceProperties.getExecutor();
        taskExecutor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        taskExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
        taskExecutor.setAllowCoreThreadTimeOut(executorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(executorProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        AsyncTaskExecutorInitializer.initAsyncTaskExecutor(taskExecutor);

        return taskExecutor;
    }

    @Bean
    public LockFactory lockFactory(@Qualifier("stringRedisTemplate") StringRedisTemplate  stringRedisTemplate) {
        return new RedisLockFactory(stringRedisTemplate,30L, TimeUnit.SECONDS);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
}
