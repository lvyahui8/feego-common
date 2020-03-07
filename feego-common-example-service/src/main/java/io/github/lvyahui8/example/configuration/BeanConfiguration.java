package io.github.lvyahui8.example.configuration;

import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;
import io.github.lvyahui8.example.service.UserService;
import io.github.lvyahui8.example.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 10:44
 */
@Configuration
@ModuleLoggerAutoGeneration({"member","rebate","item"})
public class BeanConfiguration {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
