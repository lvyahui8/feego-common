package io.github.lvyahui8.sdk.logging.autoconfigure;

import io.github.lvyahui8.sdk.logging.configuration.AbstractLogConfiguration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
}
