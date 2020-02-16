package io.github.lvyahui8.web.autoconfigure;

import io.github.lvyahui8.web.properties.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 22:33
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class})
public class WebAutoConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcBeanPostProcessor webMvcBeanPostProcessor() {
        return new WebMvcBeanPostProcessor();
    }
}
