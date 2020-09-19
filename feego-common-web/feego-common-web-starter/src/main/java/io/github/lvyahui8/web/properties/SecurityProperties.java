package io.github.lvyahui8.web.properties;

import io.github.lvyahui8.web.signature.SignatureSettings;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/14 0:39
 */
@Data
public class SecurityProperties {
    @NestedConfigurationProperty
    SignatureSettings signature;
}
