package io.github.lvyahui8.web.autoconfigure;

import io.github.lvyahui8.core.constants.Constant;
import io.github.lvyahui8.web.properties.WebProperties;
import io.github.lvyahui8.web.response.RestResponseFormatter;
import io.github.lvyahui8.web.signature.SignatureService;
import io.github.lvyahui8.web.signature.impl.SignatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 22:33
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    WebProperties webProperties;

    @Bean
    public WebMvcBeanPostProcessor webMvcBeanPostProcessor() {
        return new WebMvcBeanPostProcessor();
    }

    @Bean
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX,name = ".web.formatResponse")
    public RestResponseFormatter restResponseFormatter() {
        return new RestResponseFormatter();
    }

    @Bean
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX,name = ".web.security.signature.open",matchIfMissing = false)
    public SignatureService signatureService() {
        return new SignatureServiceImpl(webProperties.getSecurity().getSignature());
    }
}
