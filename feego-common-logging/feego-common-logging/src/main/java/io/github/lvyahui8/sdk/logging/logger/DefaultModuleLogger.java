package io.github.lvyahui8.sdk.logging.logger;

import io.github.lvyahui8.sdk.logging.handler.DefaultLogHandler;
import io.github.lvyahui8.sdk.logging.handler.LogHandler;
import org.slf4j.Logger;
import org.slf4j.event.Level;


/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:27
 */
public class DefaultModuleLogger implements ModuleLogger {
    private final Logger logger;

    private final Logger monitorLogger;

    private final String fieldSeparator;

    private final LogHandler logHandler;

    private final String enumLoggerName;

    public DefaultModuleLogger(String enumLoggerName,Logger logger, Logger monitorLogger , String fieldSeparator, LogHandler logHandler) {
        this.enumLoggerName = enumLoggerName;
        this.logger = logger;
        this.fieldSeparator = fieldSeparator;
        this.monitorLogger = monitorLogger;
        this.logHandler = logHandler == null ? new DefaultLogHandler() : logHandler;
    }

    @Override
    public ModuleLogger getInnerLogger() {
        return this;
    }

    @Override
    public void monitor(LogSchema schema) {
        LogSchema.Detail detail =  logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        monitorLogger.info(detail.getPattern(),detail.getArgs());
    }

    private Level getRuntimeLevel() {
        return logHandler.runtimeLevel(enumLoggerName);
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
    public boolean isErrorEnabled() {
        return getRuntimeLevel().toInt() <= Level.ERROR.toInt();
    }

    @Override
    public void info(LogSchema schema) {
        if (! isInfoEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        logger.info(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void warn(LogSchema schema) {
        if (! isWarnEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        logger.warn(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void warn(LogSchema schema, Throwable t) {
        if (! isWarnEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator, 1);
        detail.getArgs()[detail.getArgs().length - 1] = t;
        logger.warn(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void debug(LogSchema schema) {
        if (! isDebugEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        logger.debug(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void trace(LogSchema schema) {
        if (! isTraceEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        logger.trace(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void error(LogSchema schema) {
        if (! isErrorEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator);
        logger.error(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void error(LogSchema schema, Throwable t) {
        if (! isErrorEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(schema).buildDetail(fieldSeparator,1);
        // https://stackoverflow.com/questions/45054154/logger-format-and-throwable-slf4j-arguments/45054272#45054272
        // 关键代码：
        // org.apache.logging.log4j.message.ParameterizedMessage.initThrowable
        //   if (usedParams < argCount && this.throwable == null && params[argCount - 1] instanceof Throwable) {
        detail.getArgs()[detail.getArgs().length - 1] = t;
        logger.error(detail.getPattern(),detail.getArgs());
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
        if (! isTraceEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(LogSchema.empty()).buildDetail(fieldSeparator, format, arguments);
        logger.trace(detail.getPattern(),detail.getArgs());
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
        if (! isDebugEnabled())  return ;
        LogSchema.Detail detail = logHandler.beforeOutput(LogSchema.empty()).buildDetail(fieldSeparator, format, arguments);
        logger.debug(detail.getPattern(),detail.getArgs());
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
        if (! isInfoEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(LogSchema.empty()).buildDetail(fieldSeparator, format, arguments);
        logger.info(detail.getPattern(),detail.getArgs());
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
        if (! isWarnEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(LogSchema.empty()).buildDetail(fieldSeparator, format, arguments);
        logger.warn(detail.getPattern(),detail.getArgs());
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
        if (! isErrorEnabled()) return;
        LogSchema.Detail detail = logHandler.beforeOutput(LogSchema.empty()).buildDetail(fieldSeparator, format, arguments);
        logger.error(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void error(String msg, Throwable t) {
        error(LogSchema.empty().of("msg",msg),t);
    }
}
