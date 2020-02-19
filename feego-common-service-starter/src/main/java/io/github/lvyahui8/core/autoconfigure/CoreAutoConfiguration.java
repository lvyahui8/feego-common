package io.github.lvyahui8.core.autoconfigure;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import io.github.lvyahui8.core.constants.Constant;
import io.github.lvyahui8.core.properties.ExecutorProperties;
import io.github.lvyahui8.core.properties.LoggingProperties;
import io.github.lvyahui8.core.properties.ServiceProperties;
import io.github.lvyahui8.core.utils.AsyncTaskExecutorInitializer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:00
 */
@Configuration
@EnableConfigurationProperties({ServiceProperties.class})
public class CoreAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ServiceProperties serviceProperties;

    private static final Map<String, String> SYSTEMS;

    static {
        Map<String, String> systems = new LinkedHashMap<String, String>();
        systems.put("ch.qos.logback.core.Appender",
                "org.springframework.boot.logging.logback.LogbackLoggingSystem");
        systems.put("org.apache.logging.log4j.core.impl.Log4jContextFactory",
                "org.springframework.boot.logging.log4j2.Log4J2LoggingSystem");
        /// systems.put("java.util.logging.LogManager",
        ///        "org.springframework.boot.logging.java.JavaLoggingSystem");
        SYSTEMS = Collections.unmodifiableMap(systems);
    }


    @Bean
    @ConditionalOnProperty(prefix = Constant.CONFIG_PREFIX ,name =  "executor-properties.open")
    Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        ExecutorProperties executorProperties = serviceProperties.getExecutorProperties();
        taskExecutor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        taskExecutor.setCorePoolSize(executorProperties.getCorePoolSize());
        taskExecutor.setAllowCoreThreadTimeOut(executorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(executorProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        AsyncTaskExecutorInitializer.initAsyncTaskExecutor(taskExecutor);
        return taskExecutor;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LoggingProperties loggingProperties = serviceProperties.getLoggingProperties();
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        String loggerName = "test";
        Logger logger = context.getLogger(loggerName);
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setName(loggerName + "Appender");
        String fileName = "F:" + File.separator + loggerName + ".log";
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
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} : %m%n");
        patternLayoutEncoder.start();
        rollingFileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
        rollingFileAppender.setEncoder(patternLayoutEncoder);
        logger.addAppender(rollingFileAppender);
        for (int i = 0; i < 1000; i++) {
            logger.info("xxx");
        }
    }
}
