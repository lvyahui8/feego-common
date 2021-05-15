package io.github.lvyahui8.sdk.logging.context;

import io.github.lvyahui8.sdk.logging.event.LogEvent;
import org.slf4j.event.Level;

import java.util.Queue;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/4/30
 */
public class LogContext {
    String biz;

    /**
     * transaction id/request id/trace id
     */
    String tid;

    Level level;

    boolean hasError;

    Queue<LogEvent> discardedEventQueue;

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Queue<LogEvent> getDiscardedEventQueue() {
        return discardedEventQueue;
    }

    public void setDiscardedEventQueue(Queue<LogEvent> discardedEventQueue) {
        this.discardedEventQueue = discardedEventQueue;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
