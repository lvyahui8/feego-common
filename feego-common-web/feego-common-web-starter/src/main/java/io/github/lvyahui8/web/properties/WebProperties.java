package io.github.lvyahui8.web.properties;

import io.github.lvyahui8.sdk.constants.Constant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 22:46
 */
@ConfigurationProperties(prefix = Constant.CONFIG_PREFIX + ".web")
@Data
public class WebProperties {
    /**
     * 格式化响应
     */
    boolean formatResponse =  true;

    @NestedConfigurationProperty
    SecurityProperties security;
}
