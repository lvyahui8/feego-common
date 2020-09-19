package io.github.lvyahui8.core.logging;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:08
 */
public interface ModuleLogger extends Logger {
    default void monitor(LogSchema schema) { ((ModuleLogger) getInnerLogger()).monitor(schema); }

    default void trace(LogSchema schema) {
        ((ModuleLogger) getInnerLogger()).trace(schema);
    }

    default void debug(LogSchema schema) {
        ((ModuleLogger) getInnerLogger()).debug(schema);
    }

    default void info(LogSchema schema) {
        ((ModuleLogger) getInnerLogger()).info(schema);
    }

    default void warn(LogSchema schema) {
        ((ModuleLogger) getInnerLogger()).warn(schema);
    }

    default void warn(LogSchema schema,Throwable t) {
        ((ModuleLogger) getInnerLogger()).warn(schema,t);
    }

    default void error(LogSchema schema) {
        ((ModuleLogger) getInnerLogger()).error(schema);
    }

    default void error(LogSchema schema,Throwable t) {
        ((ModuleLogger) getInnerLogger()).error(schema,t);
    }

    @Override
    default String getName() {
        return getInnerLogger().getName();
    }

    @Override
    default boolean isTraceEnabled() {
        return getInnerLogger().isTraceEnabled();
    }

    @Override
    default void trace(String msg) {
        getInnerLogger().trace(msg);
    }

    @Override
    default void trace(String format, Object arg) {
        getInnerLogger().trace(format,arg);
    }

    @Override
    default void trace(String format, Object arg1, Object arg2) {
        getInnerLogger().trace(format,arg1,arg2);
    }

    @Override
    default void trace(String format, Object... arguments) {
        getInnerLogger().trace(format, arguments);
    }

    @Override
    default void trace(String msg, Throwable t) {
        getInnerLogger().trace(msg,t);
    }

    @Override
    default boolean isTraceEnabled(Marker marker) {
        return getInnerLogger().isTraceEnabled(marker);
    }

    @Override
    default void trace(Marker marker, String msg) {
        getInnerLogger().trace(marker,msg);
    }

    @Override
    default void trace(Marker marker, String format, Object arg) {
        getInnerLogger().trace(marker,format,arg);
    }

    @Override
    default void trace(Marker marker, String format, Object arg1, Object arg2) {
        getInnerLogger().trace(marker, format, arg1, arg2);
    }

    @Override
    default void trace(Marker marker, String format, Object... argArray) {
        getInnerLogger().trace(marker,format,argArray);
    }

    @Override
    default void trace(Marker marker, String msg, Throwable t) {
        getInnerLogger().trace(marker,msg,t);
    }

    @Override
    default boolean isDebugEnabled() {
        return getInnerLogger().isDebugEnabled();
    }

    @Override
    default void debug(String msg) {
        getInnerLogger().debug(msg);
    }

    @Override
    default void debug(String format, Object arg) {
        getInnerLogger().debug(format,arg);
    }

    @Override
    default void debug(String format, Object arg1, Object arg2) {
        getInnerLogger().debug(format, arg1, arg2);
    }

    @Override
    default void debug(String format, Object... arguments) {
        getInnerLogger().debug(format, arguments);
    }

    @Override
    default void debug(String msg, Throwable t) {
        getInnerLogger().debug(msg,t);
    }

    @Override
    default boolean isDebugEnabled(Marker marker) {
        return getInnerLogger().isDebugEnabled(marker);
    }

    @Override
    default void debug(Marker marker, String msg) {
        getInnerLogger().debug(marker,msg);
    }

    @Override
    default void debug(Marker marker, String format, Object arg) {
        getInnerLogger().debug(marker,format,arg);
    }

    @Override
    default void debug(Marker marker, String format, Object arg1, Object arg2) {
        getInnerLogger().debug(marker, format, arg1, arg2);
    }

    @Override
    default void debug(Marker marker, String format, Object... arguments) {
        getInnerLogger().debug(marker, format, arguments);
    }

    @Override
    default void debug(Marker marker, String msg, Throwable t) {
        getInnerLogger().debug(marker, msg, t);
    }

    @Override
    default boolean isInfoEnabled() {
        return getInnerLogger().isInfoEnabled();
    }

    @Override
    default void info(String msg) {
        getInnerLogger().info(msg);
    }

    @Override
    default void info(String format, Object arg) {
        getInnerLogger().info(format,arg);
    }

    @Override
    default void info(String format, Object arg1, Object arg2) {
        getInnerLogger().info(format, arg1, arg2);
    }

    @Override
    default void info(String format, Object... arguments) {
        getInnerLogger().info(format, arguments);
    }

    @Override
    default void info(String msg, Throwable t) {
        getInnerLogger().info(msg, t);
    }

    @Override
    default boolean isInfoEnabled(Marker marker) {
        return getInnerLogger().isInfoEnabled(marker);
    }

    @Override
    default void info(Marker marker, String msg) {
        getInnerLogger().info(marker, msg);
    }

    @Override
    default void info(Marker marker, String format, Object arg) {
        getInnerLogger().info(marker, format, arg);
    }

    @Override
    default void info(Marker marker, String format, Object arg1, Object arg2) {
        getInnerLogger().info(marker, format, arg1, arg2);
    }

    @Override
    default void info(Marker marker, String format, Object... arguments) {
        getInnerLogger().info(marker, format, arguments);
    }

    @Override
    default void info(Marker marker, String msg, Throwable t) {
        getInnerLogger().info(marker, msg, t);
    }

    @Override
    default boolean isWarnEnabled() {
        return getInnerLogger().isWarnEnabled();
    }

    @Override
    default void warn(String msg) {
        getInnerLogger().warn(msg);
    }

    @Override
    default void warn(String format, Object arg) {
        getInnerLogger().warn(format,arg);
    }

    @Override
    default void warn(String format, Object... arguments) {
        getInnerLogger().warn(format,arguments);
    }

    @Override
    default void warn(String format, Object arg1, Object arg2) {
        getInnerLogger().warn(format, arg1, arg2);
    }

    @Override
    default void warn(String msg, Throwable t) {
        getInnerLogger().warn(msg,t);
    }

    @Override
    default boolean isWarnEnabled(Marker marker) {
        return getInnerLogger().isWarnEnabled(marker);
    }

    @Override
    default void warn(Marker marker, String msg) {
        getInnerLogger().warn(msg);
    }

    @Override
    default void warn(Marker marker, String format, Object arg) {
        getInnerLogger().warn(marker, format, arg);
    }

    @Override
    default void warn(Marker marker, String format, Object arg1, Object arg2) {
        getInnerLogger().warn(marker, format, arg1, arg2);
    }

    @Override
    default void warn(Marker marker, String format, Object... arguments) {
        getInnerLogger().warn(marker, format, arguments);
    }

    @Override
    default void warn(Marker marker, String msg, Throwable t) {
        getInnerLogger().warn(marker, msg, t);
    }

    @Override
    default boolean isErrorEnabled() {
        return getInnerLogger().isErrorEnabled();
    }

    @Override
    default void error(String msg) {
        getInnerLogger().error(msg);
    }

    @Override
    default void error(String format, Object arg) {
        getInnerLogger().error(format,arg);
    }

    @Override
    default void error(String format, Object arg1, Object arg2) {
        getInnerLogger().error(format, arg1, arg2);
    }

    @Override
    default void error(String format, Object... arguments) {
        getInnerLogger().error(format, arguments);
    }

    @Override
    default void error(String msg, Throwable t) {
        getInnerLogger().error(msg,t);
    }

    @Override
    default boolean isErrorEnabled(Marker marker) {
        return getInnerLogger().isErrorEnabled();
    }

    @Override
    default void error(Marker marker, String msg) {
        getInnerLogger().error(marker,msg);
    }

    @Override
    default void error(Marker marker, String format, Object arg) {
        getInnerLogger().error(marker,format,arg);
    }

    @Override
    default void error(Marker marker, String format, Object arg1, Object arg2) {
        getInnerLogger().error(marker, format, arg1, arg2);
    }

    @Override
    default void error(Marker marker, String format, Object... arguments) {
        getInnerLogger().error(marker,format,arguments);
    }

    @Override
    default void error(Marker marker, String msg, Throwable t) {
        getInnerLogger().error(marker,msg,t);
    }

    /**
     * get actual logger
     * @return actual logger
     */
    Logger getInnerLogger() ;
}
