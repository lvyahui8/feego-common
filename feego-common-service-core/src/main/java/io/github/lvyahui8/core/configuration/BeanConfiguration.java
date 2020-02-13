package io.github.lvyahui8.core.configuration;

import io.github.lvyahui8.core.properties.ExecutorProperties;
import io.github.lvyahui8.core.properties.ServiceProperties;
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
public class BeanConfiguration {

    @Autowired
    ServiceProperties serviceProperties;

    @Bean
    @ConditionalOnProperty(name = Constant.CONFIG_PREFIX + ".executorSwitch", value = {"true"})
    Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        ExecutorProperties executorProperties = serviceProperties.getExecutorProperties();
        taskExecutor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        taskExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
        taskExecutor.setAllowCoreThreadTimeOut(executorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(executorProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        return taskExecutor;
    }
}
