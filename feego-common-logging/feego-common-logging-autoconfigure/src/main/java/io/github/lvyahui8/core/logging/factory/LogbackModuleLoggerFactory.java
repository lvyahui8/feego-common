package io.github.lvyahui8.core.logging.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import io.github.lvyahui8.core.logging.autoconfigure.ModuleLoggerProperties;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/23 19:24
 */
public class LogbackModuleLoggerFactory implements ModuleLoggerFactory {

    private ModuleLoggerProperties loggingProperties;

    public LogbackModuleLoggerFactory(ModuleLoggerProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Override
    public Logger getLogger(String pattern, String loggerName, ILoggerFactory loggerFactory, String fileName, String fileNamePattern) {
        ch.qos.logback.classic.LoggerContext context = (ch.qos.logback.classic.LoggerContext)  loggerFactory;

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

        ch.qos.logback.classic.Logger logger = context.getLogger(loggerName);
        logger.setAdditive(false);
        logger.setLevel(Level.DEBUG);

        logger.addAppender(rollingFileAppender);
        return logger;
    }
}
