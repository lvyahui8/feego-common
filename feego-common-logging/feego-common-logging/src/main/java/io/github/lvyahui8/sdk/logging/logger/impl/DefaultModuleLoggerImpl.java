package io.github.lvyahui8.sdk.logging.logger.impl;

import io.github.lvyahui8.sdk.logging.schema.LogSchema;
import io.github.lvyahui8.sdk.logging.logger.ModuleLogger;
import org.slf4j.Logger;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:27
 */
public class DefaultModuleLoggerImpl implements ModuleLogger {
    private Logger logger;

    private Logger monitorLogger;

    private String fieldSeparator;

    public DefaultModuleLoggerImpl(Logger logger, Logger monitorLogger ,String fieldSeparator) {
        this.logger = logger;
        this.fieldSeparator = fieldSeparator;
        this.monitorLogger = monitorLogger;
    }

    @Override
    public ModuleLogger getInnerLogger() {
        return this;
    }

    @Override
    public void monitor(LogSchema schema) {
        LogSchema.Detail detail =  schema.buildDetail(fieldSeparator);
        monitorLogger.info(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void info(LogSchema schema) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator);
        logger.info(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void warn(LogSchema schema) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator);
        logger.warn(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void warn(LogSchema schema, Throwable t) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator, 1);
        detail.getArgs()[detail.getArgs().length - 1] = t;
        logger.warn(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void debug(LogSchema schema) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator);
        logger.debug(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void trace(LogSchema schema) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator);
        logger.trace(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void error(LogSchema schema) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator);
        logger.error(detail.getPattern(),detail.getArgs());
    }

    @Override
    public void error(LogSchema schema, Throwable t) {
        LogSchema.Detail detail = schema.buildDetail(fieldSeparator,1);
        // https://stackoverflow.com/questions/45054154/logger-format-and-throwable-slf4j-arguments/45054272#45054272
        // 关键代码：
        // org.apache.logging.log4j.message.ParameterizedMessage.initThrowable
        //   if (usedParams < argCount && this.throwable == null && params[argCount - 1] instanceof Throwable) {
        detail.getArgs()[detail.getArgs().length - 1] = t;
        logger.error(detail.getPattern(),detail.getArgs());
    }
    
    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void trace(String msg) {
        trace(LogSchema.empty().of("msg",msg));
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
        LogSchema.Detail detail = LogSchema.empty().buildDetail(fieldSeparator,arguments != null ? arguments.length : 0);
        String pattern = detail.getPattern() + "msg:" + format + fieldSeparator;
        Object[] args ;
        if (arguments != null && arguments.length > 0 ) {
            args = new Object[detail.getArgs().length + arguments.length];
            System.arraycopy(detail.getArgs(),0,args,0,detail.getArgs().length);
            System.arraycopy(arguments,0,args,detail.getArgs().length,arguments.length);
        } else {
            args = detail.getArgs();
        }

        logger.trace(pattern,args);
    }

    @Override
    public void trace(String msg, Throwable t) {
        logger.trace(msg,t);
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        logger.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        logger.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        logger.debug(msg,t);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        logger.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logger.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        logger.info(msg,t);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        logger.warn(format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        logger.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        logger.warn(msg,t);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        logger.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        logger.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        logger.error(msg,t);
    }
}
