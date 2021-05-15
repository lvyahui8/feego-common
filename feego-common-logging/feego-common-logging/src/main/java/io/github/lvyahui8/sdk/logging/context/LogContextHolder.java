package io.github.lvyahui8.sdk.logging.context;


import io.github.lvyahui8.sdk.logging.event.LogEvent;
import org.slf4j.event.Level;

import java.util.ArrayDeque;
import java.util.Queue;

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

    public static LogContext getLogContext() {
        LogContext logContext = contextHolder.get();
        if (logContext == null) {
            putLogContext(logContext = new LogContext());
        }
        return logContext;
    }

    public static String getBiz() {
        return getLogContext().getBiz();
    }

    public static String getTid() {
        return getLogContext().getTid();
    }

    public static Level getLevel() {
        return getLogContext().getLevel();
    }

    public static void shelveLogEvent(LogEvent logEvent) {
        LogContext logContext = getLogContext();
        if (logContext.getDiscardedEventQueue() != null) {
            logContext.getDiscardedEventQueue().offer(logEvent);
        }
    }

    public static Queue<LogEvent> getDiscardedEventQueue() {
        return getLogContext().getDiscardedEventQueue();
    }

    public static void initDiscardedEventQueue(Queue<LogEvent> eventQueue) {
        LogContext logContext = contextHolder.get();
        if (logContext == null) {
            return ;
        }
        if (eventQueue != null) {
            logContext.setDiscardedEventQueue(eventQueue);
        } else {
            logContext.setDiscardedEventQueue(new ArrayDeque<>());
        }
    }
}
