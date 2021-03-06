package io.github.lvyahui8.sdk.logging.autoconfigure;

import io.github.lvyahui8.sdk.logging.logger.ModuleLogger;
import io.github.lvyahui8.sdk.logging.factory.Log4j2ModuleLoggerFactory;
import io.github.lvyahui8.sdk.logging.factory.LogbackModuleLoggerFactory;
import io.github.lvyahui8.sdk.logging.factory.ModuleLoggerFactory;
import org.reflections.Reflections;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 12:12
 */
@Configuration
@EnableConfigurationProperties(ModuleLoggerProperties.class)
public class ModuleLoggerAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ModuleLoggerProperties loggingProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();

        ModuleLoggerFactory factory ;
        if ("ch.qos.logback.classic.LoggerContext".equals(loggerFactory.getClass().getName())) {
            factory = new LogbackModuleLoggerFactory(loggingProperties);
        } else if ("org.apache.logging.slf4j.Log4jLoggerFactory".equals(loggerFactory.getClass().getName())){
            factory = new Log4j2ModuleLoggerFactory(loggingProperties);
        } else {
            throw new UnsupportedOperationException("Only logback and log4j2 are supported");
        }

        Reflections reflections = new Reflections("feego.common.");
        Set<Class<? extends ModuleLogger>> allModuleLoggers = reflections.getSubTypesOf(ModuleLogger.class);
        allModuleLoggers.addAll(loggingProperties.getModuleLoggerEnums());
        factory.initModuleLogger(allModuleLoggers.toArray(new Class[0]));
    }

}
