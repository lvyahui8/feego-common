package io.github.lvyahui8.core.properties;

import io.github.lvyahui8.core.constants.Constant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:01
 */
@ConfigurationProperties(prefix = Constant.CONFIG_PREFIX)
@Data
public class ServiceProperties {
    @NestedConfigurationProperty
    ExecutorProperties executorProperties = new ExecutorProperties();

    @NestedConfigurationProperty
    LoggingProperties loggingProperties = new LoggingProperties();
}

