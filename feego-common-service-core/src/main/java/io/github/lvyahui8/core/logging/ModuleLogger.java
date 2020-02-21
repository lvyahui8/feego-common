package io.github.lvyahui8.core.logging;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:08
 */
public interface ModuleLogger extends Logger {

    /**
     * get actual logger
     * @return actual logger
     */
    public Logger getInnerLogger() ;

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

    }

    @Override
    default void trace(String format, Object arg1, Object arg2) {

    }

    @Override
    default void trace(String format, Object... arguments) {

    }

    @Override
    default void trace(String msg, Throwable t) {

    }

    @Override
    default boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    default void trace(Marker marker, String msg) {

    }

    @Override
    default void trace(Marker marker, String format, Object arg) {

    }

    @Override
    default void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    default void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    default void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    default boolean isDebugEnabled() {
        return false;
    }

    @Override
    default void debug(String msg) {

    }

    @Override
    default void debug(String format, Object arg) {

    }

    @Override
    default void debug(String format, Object arg1, Object arg2) {

    }

    @Override
    default void debug(String format, Object... arguments) {

    }

    @Override
    default void debug(String msg, Throwable t) {

    }

    @Override
    default boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    default void debug(Marker marker, String msg) {

    }

    @Override
    default void debug(Marker marker, String format, Object arg) {

    }

    @Override
    default void debug(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    default void debug(Marker marker, String format, Object... arguments) {

    }

    @Override
    default void debug(Marker marker, String msg, Throwable t) {

    }

    @Override
    default boolean isInfoEnabled() {
        return false;
    }

    @Override
    default void info(String msg) {
        getInnerLogger().info(msg);
    }

    @Override
    default void info(String format, Object arg) {

    }

    @Override
    default void info(String format, Object arg1, Object arg2) {

    }

    @Override
    default void info(String format, Object... arguments) {

    }

    @Override
    default void info(String msg, Throwable t) {

    }

    @Override
    default boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    default void info(Marker marker, String msg) {

    }

    @Override
    default void info(Marker marker, String format, Object arg) {

    }

    @Override
    default void info(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    default void info(Marker marker, String format, Object... arguments) {

    }

    @Override
    default void info(Marker marker, String msg, Throwable t) {

    }

    @Override
    default boolean isWarnEnabled() {
        return false;
    }

    @Override
    default void warn(String msg) {

    }

    @Override
    default void warn(String format, Object arg) {

    }

    @Override
    default void warn(String format, Object... arguments) {

    }

    @Override
    default void warn(String format, Object arg1, Object arg2) {

    }

    @Override
    default void warn(String msg, Throwable t) {

    }

    @Override
    default boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    default void warn(Marker marker, String msg) {

    }

    @Override
    default void warn(Marker marker, String format, Object arg) {

    }

    @Override
    default void warn(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    default void warn(Marker marker, String format, Object... arguments) {

    }

    @Override
    default void warn(Marker marker, String msg, Throwable t) {

    }

    @Override
    default boolean isErrorEnabled() {
        return false;
    }

    @Override
    default void error(String msg) {

    }

    @Override
    default void error(String format, Object arg) {

    }

    @Override
    default void error(String format, Object arg1, Object arg2) {

    }

    @Override
    default void error(String format, Object... arguments) {

    }

    @Override
    default void error(String msg, Throwable t) {

    }

    @Override
    default boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    default void error(Marker marker, String msg) {

    }

    @Override
    default void error(Marker marker, String format, Object arg) {

    }

    @Override
    default void error(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    default void error(Marker marker, String format, Object... arguments) {

    }

    @Override
    default void error(Marker marker, String msg, Throwable t) {

    }
}
