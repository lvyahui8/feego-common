package io.github.lvyahui8.sdk.logging.logger;

import io.github.lvyahui8.sdk.logging.context.LogContextHolder;
import io.github.lvyahui8.sdk.logging.event.LogEvent;
import io.github.lvyahui8.sdk.logging.handler.DefaultLogHandler;
import io.github.lvyahui8.sdk.logging.handler.LogHandler;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;


/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:27
 */
public class DefaultModuleLogger implements ModuleLogger {
    private final Logger logger;

    private final Logger monitorLogger;

    private final Logger errorLogger;

    private final String fieldSeparator;
    private LogHandler logHandler;

    private final String enumLoggerName;

    public DefaultModuleLogger(String enumLoggerName,Logger logger,Logger errorLogger, Logger monitorLogger , String fieldSeparator, LogHandler logHandler) {
        this.enumLoggerName = enumLoggerName;
        this.logger = logger;
        this.fieldSeparator = fieldSeparator;
        this.monitorLogger = monitorLogger;
        this.errorLogger = errorLogger;
        this.logHandler = logHandler == null ? new DefaultLogHandler() : logHandler;
    }

    @Override
    public ModuleLogger getInnerLogger() {
        return this;
    }

    protected void setLogHandler(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    @Override
    public void monitor(LogSchema schema) {
        LogSchema.Detail detail =  logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        monitorLogger.info(detail.getPattern(),detail.getArgs());
    }

    private Level getRuntimeLevel() {
        Level level = logHandler.runtimeLevel(enumLoggerName);
        return level != null ? level : Level.INFO;
    }

    @Override
    public boolean isTraceEnabled() {
        return getRuntimeLevel().toInt() <= Level.TRACE.toInt();
    }

    @Override
    public boolean isDebugEnabled() {
        return getRuntimeLevel().toInt() <= Level.DEBUG.toInt();
    }

    @Override
    public boolean isInfoEnabled() {
        return getRuntimeLevel().toInt() <= Level.INFO.toInt();
    }

    @Override
    public boolean isWarnEnabled() {
        return getRuntimeLevel().toInt() <= Level.WARN.toInt();
    }

    @Override
    public void info(LogSchema schema) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.INFO, logger);
        if (isInfoEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void warn(LogSchema schema) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.WARN, logger);
        if (isWarnEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void warn(LogSchema schema, Throwable t) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.WARN, logger,t);
        if (isWarnEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void debug(LogSchema schema) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.DEBUG, logger);
        if (isDebugEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void trace(LogSchema schema) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.TRACE, logger);
        if (isTraceEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void error(LogSchema schema) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.ERROR, errorLogger);
        LogEventReactor.recordingEvent(logEvent);
    }

    @Override
    public void error(LogSchema schema, Throwable t) {
        LogEvent logEvent = new LogEvent(logHandler.beforeOutput(schema).setFieldSeparator(fieldSeparator), Level.ERROR, errorLogger,t);
        LogEventReactor.recordingEvent(logEvent);
    }

    @Override
    public void trace(String msg) {
        trace(logHandler.beforeOutput(LogSchema.empty()).of("msg",msg));
    }

    @Override
    public void trace(String format, Object arg) {
        trace(format, new Object[]{arg});
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        trace(format, new Object[]{ arg1, arg2});
    }

    @Override
    public void trace(String format, Object... arguments) {
        LogSchema logSchema = logHandler.beforeOutput(LogSchema.empty()).setFieldSeparator(fieldSeparator).msgPattern(format).msgArgs(arguments);
        LogEvent logEvent = new LogEvent(logSchema, Level.TRACE, logger);
        if (isTraceEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void debug(String msg) {
        debug(LogSchema.empty().of("msg",msg));
    }

    @Override
    public void debug(String format, Object arg) {
        debug(format, new Object[]{arg});
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        debug(format, new Object[]{arg1,arg2});
    }

    @Override
    public void debug(String format, Object... arguments) {
        LogSchema logSchema = logHandler.beforeOutput(LogSchema.empty()).setFieldSeparator(fieldSeparator).msgPattern(format).msgArgs(arguments);
        LogEvent logEvent = new LogEvent(logSchema, Level.DEBUG, logger);
        if (isDebugEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void info(String msg) {
        info(LogSchema.empty().of("msg",msg));
    }

    @Override
    public void info(String format, Object arg) {
        info(format, new Object[]{arg});
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        info(format, new Object[]{arg1,arg2});
    }

    @Override
    public void info(String format, Object... arguments) {
        LogSchema logSchema = logHandler.beforeOutput(LogSchema.empty()).setFieldSeparator(fieldSeparator).msgPattern(format).msgArgs(arguments);
        LogEvent logEvent = new LogEvent(logSchema, Level.INFO, logger);
        if (isInfoEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void warn(String msg) {
        warn(LogSchema.empty().of("msg",msg));
    }

    @Override
    public void warn(String format, Object arg) {
        warn(format, new Object[]{arg});
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        warn(format, new Object[]{arg1,arg2});
    }

    @Override
    public void warn(String format, Object... arguments) {
        LogSchema logSchema = logHandler.beforeOutput(LogSchema.empty()).setFieldSeparator(fieldSeparator).msgPattern(format).msgArgs(arguments);
        LogEvent logEvent = new LogEvent(logSchema, Level.WARN, logger);
        if (isWarnEnabled()) {
            LogEventReactor.recordingEvent(logEvent);
        } else {
            LogContextHolder.shelveLogEvent(logEvent);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        warn(LogSchema.empty().of("msg",msg),t);
    }

    @Override
    public void error(String msg) {
        error(LogSchema.empty().of("msg",msg));
    }

    @Override
    public void error(String format, Object arg) {
        error(format, new Object[]{arg});
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        error(format, new Object[]{arg1,arg2});
    }

    @Override
    public void error(String format, Object... arguments) {
        LogSchema logSchema = logHandler.beforeOutput(LogSchema.empty()).setFieldSeparator(fieldSeparator).msgPattern(format).msgArgs(arguments);
        LogEvent logEvent = new LogEvent(logSchema, Level.WARN, errorLogger);
        LogEventReactor.recordingEvent(logEvent);
    }

    @Override
    public void error(String msg, Throwable t) {
        error(LogSchema.empty().of("msg",msg),t);
    }
}
