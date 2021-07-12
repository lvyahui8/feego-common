package io.github.lvyahui8.sdk.autoconfigure;


import io.github.lvyahui8.sdk.constants.Constant;
import io.github.lvyahui8.sdk.properties.ExecutorProperties;
import io.github.lvyahui8.sdk.properties.ServiceProperties;
import io.github.lvyahui8.sdk.reddot.DefaultRedDotManager;
import io.github.lvyahui8.sdk.reddot.RedDotManager;
import io.github.lvyahui8.sdk.utils.AsyncTaskExecutorInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.ref.Reference;
import java.util.concurrent.Executor;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:00
 */
@Configuration
@EnableConfigurationProperties({ServiceProperties.class})
public class CoreAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ServiceProperties serviceProperties;


    @Bean("applicationTaskExecutor")
    public Executor applicationTaskExecutor(){
        return taskExecutor();
    }

    @Bean("taskScheduler")
    public Executor taskScheduler() {
        return taskExecutor();
    }

    @Bean("taskExecutor")
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX ,name =  ".executor.open",matchIfMissing = true)
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        ExecutorProperties executorProperties = serviceProperties.getExecutor();
        taskExecutor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        taskExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
        taskExecutor.setAllowCoreThreadTimeOut(executorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(executorProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        taskExecutor.setThreadFactory(new CustomizableThreadFactory("tp_"));
        AsyncTaskExecutorInitializer.initAsyncTaskExecutor(taskExecutor);

        return taskExecutor;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedDotManager redDotManager(RedisTemplate<Object,Object> redisTemplate) {
        return new DefaultRedDotManager(redisTemplate);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

    }
}
