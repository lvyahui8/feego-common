package io.github.lvyahui8.sdk.logging.logger;

import io.github.lvyahui8.sdk.logging.context.LogContextHolder;
import io.github.lvyahui8.sdk.logging.event.LogEvent;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Queue;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/15
 */
public class LogEventReactor {
    public static void recordingEvent(LogEvent logEvent) {
        Level level = logEvent.getLevel();
        Throwable throwable = logEvent.getThrowable();
        LogSchema.Detail detail ;
        if (throwable == null) {
            detail = logEvent.getSchema().buildDetail(null);
        } else {
            // https://stackoverflow.com/questions/45054154/logger-format-and-throwable-slf4j-arguments/45054272#45054272
            // 关键代码：
            // org.apache.logging.log4j.message.ParameterizedMessage.initThrowable
            //   if (usedParams < argCount && this.throwable == null && params[argCount - 1] instanceof Throwable) {
            detail = logEvent.getSchema().buildDetail(null,1);
            detail.getArgs()[detail.getArgs().length - 1] = throwable;
        }
        Logger logger = logEvent.getLogger();
        switch (level) {
            case TRACE:
                logger.trace(detail.getPattern(),detail.getArgs());
                break;
            case DEBUG:
                logger.debug(detail.getPattern(),detail.getArgs());
                break;
            case INFO:
                logger.info(detail.getPattern(),detail.getArgs());
                break;
            case WARN:
                logger.warn(detail.getPattern(),detail.getArgs());
                break;
            case ERROR:
                LogContextHolder.getLogContext().setHasError(true);
                logger.error(detail.getPattern(),detail.getArgs());
                break;
            default:
                break;
        }
    }

    public static void replayDiscardedEvents() {
        Queue<LogEvent> queue = LogContextHolder.getDiscardedEventQueue();
        if (queue != null) {
            LogEvent event ;
            while((event = queue.poll()) != null) {
                recordingEvent(event);
            }
            LogContextHolder.getLogContext().setDiscardedEventQueue(null);
        }
    }
}
