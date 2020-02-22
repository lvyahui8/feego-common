package io.github.lvyahui8.core.logging.autoconfigure;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import io.github.lvyahui8.core.logging.ModuleLogger;
import io.github.lvyahui8.core.logging.ModuleLoggerRepository;
import io.github.lvyahui8.core.logging.impl.DefaultModuleLoggerImpl;
import org.reflections.Reflections;
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
        String storagePath = (loggingProperties.getStoragePath() == null ? System.getProperty("user.home") : loggingProperties.getStoragePath())
                + File.separator + "logs";

        Reflections reflections = new Reflections("feego.common.");
        Set<Class<? extends ModuleLogger>> allModuleLoggers = reflections.getSubTypesOf(ModuleLogger.class);

        for (Class<? extends ModuleLogger> moduleEnumClass : allModuleLoggers) {
            for (Object enumInstance : moduleEnumClass.getEnumConstants()) {
                Enum<?> em  = (Enum<?>) enumInstance;

                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                String loggerName = em.name();


                PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
                patternLayoutEncoder.setContext(context);
                patternLayoutEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} : %m%n");
                patternLayoutEncoder.start();

                RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
                rollingFileAppender.setName(loggerName + "Appender");

                String fileName = storagePath + File.separator + loggerName + ".log";
                rollingFileAppender.setFile(fileName);
                rollingFileAppender.setContext(context);
                rollingFileAppender.setAppend(true);
                SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy = new SizeAndTimeBasedRollingPolicy();
                sizeAndTimeBasedRollingPolicy.setContext(context);
                sizeAndTimeBasedRollingPolicy.setMaxFileSize(FileSize.valueOf(loggingProperties.getMaxFileSize()));
                sizeAndTimeBasedRollingPolicy.setTotalSizeCap(FileSize.valueOf(loggingProperties.getTotalSizeCap()));
                sizeAndTimeBasedRollingPolicy.setMaxHistory(loggingProperties.getMaxHistory());
                sizeAndTimeBasedRollingPolicy.setParent(rollingFileAppender);
                sizeAndTimeBasedRollingPolicy.setFileNamePattern(fileName + ".%d{yyyy-MM-dd}.%i");
                sizeAndTimeBasedRollingPolicy.start();

                rollingFileAppender.setEncoder(patternLayoutEncoder);
                rollingFileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
                rollingFileAppender.start();

                Logger logger = context.getLogger(loggerName);
                logger.setAdditive(false);
                logger.setLevel(Level.DEBUG);

                logger.addAppender(rollingFileAppender);

                /* 使用代理类替换代理枚举实现 */
                ModuleLogger moduleLogger = new DefaultModuleLoggerImpl(logger,loggingProperties.getFieldSeparator());
                ModuleLoggerRepository.put(loggerName,moduleLogger);
            }
        }

    }
}
