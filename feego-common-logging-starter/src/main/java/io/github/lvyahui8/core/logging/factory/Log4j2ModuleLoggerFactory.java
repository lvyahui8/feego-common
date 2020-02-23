package io.github.lvyahui8.core.logging.factory;

import io.github.lvyahui8.core.logging.autoconfigure.ModuleLoggerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/23 19:24
 */
public class Log4j2ModuleLoggerFactory implements ModuleLoggerFactory {
    private ModuleLoggerProperties loggingProperties;

    public Log4j2ModuleLoggerFactory(ModuleLoggerProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Override
    public Logger getLogger(String pattern, String loggerName, ILoggerFactory loggerFactory, String fileName, String fileNamePattern) {
        org.apache.logging.log4j.core.LoggerContext context = (LoggerContext) LogManager.getContext(false);

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
                        TimeBasedTriggeringPolicy.newBuilder().withInterval(loggingProperties.getMaxHistory()).withModulate(true).build(),
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
        return LoggerFactory.getLogger(loggerName);
    }
}
