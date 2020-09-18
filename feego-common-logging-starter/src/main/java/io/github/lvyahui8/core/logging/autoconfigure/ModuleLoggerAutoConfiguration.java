package io.github.lvyahui8.core.logging.autoconfigure;

import io.github.lvyahui8.core.logging.ModuleLogger;
import io.github.lvyahui8.core.logging.ModuleLoggerRepository;
import io.github.lvyahui8.core.logging.factory.Log4j2ModuleLoggerFactory;
import io.github.lvyahui8.core.logging.factory.LogbackModuleLoggerFactory;
import io.github.lvyahui8.core.logging.factory.ModuleLoggerFactory;
import io.github.lvyahui8.core.logging.impl.DefaultModuleLoggerImpl;
import org.reflections.Reflections;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.io.File;
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
        String storagePath = (loggingProperties.getStoragePath() == null ?
                System.getProperty("user.home") : loggingProperties.getStoragePath()) + File.separator + "logs";

        Reflections reflections = new Reflections("feego.common.");
        Set<Class<? extends ModuleLogger>> allModuleLoggers = reflections.getSubTypesOf(ModuleLogger.class);


        for (Class<? extends ModuleLogger> moduleEnumClass : allModuleLoggers) {
            if (! moduleEnumClass.isEnum()) {
                continue;
            }
            for (Object enumInstance : moduleEnumClass.getEnumConstants()) {
                Enum<?> em  = (Enum<?>) enumInstance;
                ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();

                ModuleLoggerFactory factory ;
                if ("ch.qos.logback.classic.LoggerContext".equals(loggerFactory.getClass().getName())) {
                    factory = new LogbackModuleLoggerFactory(loggingProperties);
                } else if ("org.apache.logging.slf4j.Log4jLoggerFactory".equals(loggerFactory.getClass().getName())){
                    factory = new Log4j2ModuleLoggerFactory(loggingProperties);
                } else {
                    throw new UnsupportedOperationException("Only logback and log4j2 are supported");
                }
                /* 使用代理类替换代理枚举实现 */
                ModuleLogger realModuleLogger = new DefaultModuleLoggerImpl(
                        createLogger(factory,loggerFactory,storagePath, em.name(),loggingProperties.getPattern()),
                        createLogger(factory,loggerFactory,storagePath, em.name() + "-monitor",loggingProperties.getMonitorLogPattern()),
                        loggingProperties.getFieldSeparator());
                ModuleLoggerRepository.put(em.name(), realModuleLogger);
            }
        }

    }

    private Logger createLogger(ModuleLoggerFactory factory,ILoggerFactory loggerFactory,String storagePath,String loggerName,String logPattern) {
        String fileName = storagePath + File.separator + loggerName + ".log";
        String fileNamePattern = fileName + loggingProperties.getFilePattern();

        File file = new File(fileName);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new RuntimeException("No permission to create log path!");
        }

        return factory.getLogger(logPattern, loggerName, loggerFactory, fileName, fileNamePattern);
    }

}
