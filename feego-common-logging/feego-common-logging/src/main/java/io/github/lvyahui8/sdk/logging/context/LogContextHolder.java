package io.github.lvyahui8.sdk.logging.context;

import com.sun.istack.internal.Nullable;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/4/30
 */
public class LogContextHolder {
    public static final ThreadLocal<LogContext> contextHolder = new ThreadLocal<>();

    public static void resetLogContext() {
        contextHolder.remove();
    }

    public static void putLogContext(LogContext logContext){
        if (logContext == null) {
            resetLogContext();
        } else {
            contextHolder.set(logContext);
        }
    }

    @Nullable
    public static LogContext getLogContext() {
        return contextHolder.get();
    }

    public static String getBiz() {
        LogContext logContext = contextHolder.get();
        return logContext == null ? null : logContext.getBiz();
    }

    public static String getTid() {
        LogContext logContext = contextHolder.get();
        return logContext == null ? null : logContext.getTid();
    }
}
