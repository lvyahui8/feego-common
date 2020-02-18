package io.github.lvyahui8.core.autoconfigure;


import io.github.lvyahui8.core.constants.Constant;
import io.github.lvyahui8.core.properties.ExecutorProperties;
import io.github.lvyahui8.core.properties.ServiceProperties;
import io.github.lvyahui8.core.utils.AsyncTaskExecutorInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:00
 */
@Configuration
@EnableConfigurationProperties({ServiceProperties.class})
public class CoreAutoConfiguration {

    @Autowired
    ServiceProperties serviceProperties;

    @Bean
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX ,name =  "executor-properties.open")
    Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        ExecutorProperties executorProperties = serviceProperties.getExecutorProperties();
        taskExecutor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        taskExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
        taskExecutor.setAllowCoreThreadTimeOut(executorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(executorProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        AsyncTaskExecutorInitializer.initAsyncTaskExecutor(taskExecutor);
        return taskExecutor;
    }
}
