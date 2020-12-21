package io.github.lvyahui8.sdk.logging.logger;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:08
 */
public interface ModuleLogger {
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


    default boolean isTraceEnabled() {
        return getInnerLogger().isTraceEnabled();
    }
    
    
    default boolean isDebugEnabled() {
        return getInnerLogger().isDebugEnabled();
    }
    
    
    default boolean isInfoEnabled() {
        return getInnerLogger().isInfoEnabled();
    }
    
    
    default boolean isWarnEnabled() {
        return getInnerLogger().isWarnEnabled();
    }
    
    
    default boolean isErrorEnabled() {
        return getInnerLogger().isErrorEnabled();
    }

    
    default void trace(String msg) {
        getInnerLogger().trace(msg);
    }

    
    default void trace(String format, Object arg) {
        getInnerLogger().trace(format,arg);
    }

    
    default void trace(String format, Object arg1, Object arg2) {
        getInnerLogger().trace(format,arg1,arg2);
    }

    
    default void trace(String format, Object... arguments) {
        getInnerLogger().trace(format, arguments);
    }

    
    default void debug(String msg) {
        getInnerLogger().debug(msg);
    }

    
    default void debug(String format, Object arg) {
        getInnerLogger().debug(format,arg);
    }

    
    default void debug(String format, Object arg1, Object arg2) {
        getInnerLogger().debug(format, arg1, arg2);
    }

    
    default void debug(String format, Object... arguments) {
        getInnerLogger().debug(format, arguments);
    }


    
    default void info(String msg) {
        getInnerLogger().info(msg);
    }

    
    default void info(String format, Object arg) {
        getInnerLogger().info(format,arg);
    }

    
    default void info(String format, Object arg1, Object arg2) {
        getInnerLogger().info(format, arg1, arg2);
    }

    
    default void info(String format, Object... arguments) {
        getInnerLogger().info(format, arguments);
    }


    default void warn(String msg) {
        getInnerLogger().warn(msg);
    }

    
    default void warn(String format, Object arg) {
        getInnerLogger().warn(format,arg);
    }

    
    default void warn(String format, Object... arguments) {
        getInnerLogger().warn(format,arguments);
    }

    
    default void warn(String format, Object arg1, Object arg2) {
        getInnerLogger().warn(format, arg1, arg2);
    }

    
    default void warn(String msg, Throwable t) {
        getInnerLogger().warn(msg,t);
    }
    

    
    default void error(String msg) {
        getInnerLogger().error(msg);
    }

    
    default void error(String format, Object arg) {
        getInnerLogger().error(format,arg);
    }

    
    default void error(String format, Object arg1, Object arg2) {
        getInnerLogger().error(format, arg1, arg2);
    }

    
    default void error(String format, Object... arguments) {
        getInnerLogger().error(format, arguments);
    }

    
    default void error(String msg, Throwable t) {
        getInnerLogger().error(msg,t);
    }

    /**
     * get actual logger
     * @return actual logger
     */
    ModuleLogger getInnerLogger() ;
}
