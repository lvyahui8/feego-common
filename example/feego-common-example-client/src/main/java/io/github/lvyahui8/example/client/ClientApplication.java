package io.github.lvyahui8.example.client;

import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;
import io.github.lvyahui8.example.client.service.OrderService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:15
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "io.github.lvyahui8.example.client")
@PropertySource("classpath:dubbo-consumer.properties")
@ModuleLoggerAutoGeneration({"order"})
public class ClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.createOrder(1L);
    }
}
