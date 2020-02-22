package io.github.lvyahui8.core.logging.autoconfigure;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import io.github.lvyahui8.core.logging.ModuleLogger;
import io.github.lvyahui8.core.logging.ModuleLoggerRepository;
import io.github.lvyahui8.core.logging.impl.DefaultModuleLoggerImpl;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.reflections.Reflections;
import org.slf4j.ILoggerFactory;
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

        String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} : %m%n";

        for (Class<? extends ModuleLogger> moduleEnumClass : allModuleLoggers) {


            for (Object enumInstance : moduleEnumClass.getEnumConstants()) {
                Enum<?> em  = (Enum<?>) enumInstance;

                String loggerName = em.name();

                ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
                org.slf4j.Logger lg = null;
                String fileName = storagePath + File.separator + loggerName + ".log";

                File file = new File(fileName);
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    throw new RuntimeException("No permission to create log path!");
                }
                String fileNamePattern = fileName + ".%d{yyyy-MM-dd}.%i";
                if (loggerFactory instanceof ch.qos.logback.classic.LoggerContext) {
                    ch.qos.logback.classic.LoggerContext context = (ch.qos.logback.classic.LoggerContext) loggerFactory;

                    PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
                    patternLayoutEncoder.setContext(context);
                    patternLayoutEncoder.setPattern(pattern);
                    patternLayoutEncoder.start();

                    RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
                    rollingFileAppender.setName(loggerName + "Appender");

                    rollingFileAppender.setFile(fileName);
                    rollingFileAppender.setContext(context);
                    rollingFileAppender.setAppend(true);
                    SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy = new SizeAndTimeBasedRollingPolicy();
                    sizeAndTimeBasedRollingPolicy.setContext(context);
                    sizeAndTimeBasedRollingPolicy.setMaxFileSize(FileSize.valueOf(loggingProperties.getMaxFileSize()));
                    sizeAndTimeBasedRollingPolicy.setTotalSizeCap(FileSize.valueOf(loggingProperties.getTotalSizeCap()));
                    sizeAndTimeBasedRollingPolicy.setMaxHistory(loggingProperties.getMaxHistory());
                    sizeAndTimeBasedRollingPolicy.setParent(rollingFileAppender);
                    sizeAndTimeBasedRollingPolicy.setFileNamePattern(fileNamePattern);
                    sizeAndTimeBasedRollingPolicy.start();

                    rollingFileAppender.setEncoder(patternLayoutEncoder);
                    rollingFileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
                    rollingFileAppender.start();

                    Logger logger = context.getLogger(loggerName);
                    logger.setAdditive(false);
                    logger.setLevel(Level.DEBUG);

                    logger.addAppender(rollingFileAppender);
                    lg = logger;
                } else if (loggerFactory instanceof org.apache.logging.log4j.core.LoggerContext){
                    org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) loggerFactory;

                    org.apache.logging.log4j.core.config.Configuration configuration = context.getConfiguration();

                    PatternLayout layout = PatternLayout.newBuilder()
                            .withConfiguration(configuration)
                            .withPattern(pattern)
                            .build();

                    RollingRandomAccessFileAppender appender = RollingRandomAccessFileAppender.newBuilder()
                            .setConfiguration(configuration)
                            .withName(loggerName + "Appender")
                            .withFileName(fileName)
                            .withFilePattern(fileNamePattern)
                            .withBufferedIo(true)
                            .withAppend(true)
                            .withLayout(layout)
                            .withPolicy(CompositeTriggeringPolicy.createPolicy(
                                    TimeBasedTriggeringPolicy.newBuilder().withInterval(loggingProperties.maxHistory).withModulate(true).build(),
                                    SizeBasedTriggeringPolicy.createPolicy(loggingProperties.getMaxFileSize())
                            ))
                            .withStrategy(DefaultRolloverStrategy.newBuilder().withMax(loggingProperties.getMaxHistory().toString()).withConfig(configuration).build())
                            .build();

                    appender.start();

                    configuration.addAppender(appender);

                    AppenderRef ref = AppenderRef.createAppenderRef(loggerName + "Appender",null,null);
                    LoggerConfig loggerConfig = LoggerConfig.createLogger(false, org.apache.logging.log4j.Level.TRACE,loggerName,
                            "false",new AppenderRef[]{ref},null,configuration,null);
                    loggerConfig.addAppender(appender,null,null);
                    configuration.addLogger(loggerName,loggerConfig);
                    context.updateLoggers();
                    lg = LoggerFactory.getLogger(loggerName);
                } else {
                    throw new UnsupportedOperationException("Only logback and log4j2 are supported");
                }



                /* 使用代理类替换代理枚举实现 */
                ModuleLogger moduleLogger = new DefaultModuleLoggerImpl(lg,loggingProperties.getFieldSeparator());
                ModuleLoggerRepository.put(loggerName,moduleLogger);
            }
        }

    }
}
