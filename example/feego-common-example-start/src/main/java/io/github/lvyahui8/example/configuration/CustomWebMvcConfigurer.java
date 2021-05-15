package io.github.lvyahui8.example.configuration;

import io.github.lvyahui8.web.wrapper.TraceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/15
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean httpRequestLogContextFilter() {
        FilterRegistrationBean<HttpRequestLogContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpRequestLogContextFilter());
        registrationBean.setName("httpRequestLogContextFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
