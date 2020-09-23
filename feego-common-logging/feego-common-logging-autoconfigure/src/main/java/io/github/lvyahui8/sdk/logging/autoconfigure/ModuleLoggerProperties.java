package io.github.lvyahui8.sdk.logging.autoconfigure;

import io.github.lvyahui8.sdk.logging.configuration.AbstractLogConfiguration;
import io.github.lvyahui8.sdk.logging.logger.ModuleLogger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 12:15
 */
@ConfigurationProperties(prefix = "feego.common.logging")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleLoggerProperties extends AbstractLogConfiguration {
    /**
     * 是否开启模块化日志
     */
    boolean      open = true;
    /**
     * 自定义的日志枚举类
     */
    Set<Class<? extends ModuleLogger>> moduleLoggerEnums = Collections.emptySet();
}
