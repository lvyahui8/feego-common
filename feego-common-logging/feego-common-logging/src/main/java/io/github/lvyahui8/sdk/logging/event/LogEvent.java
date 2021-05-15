package io.github.lvyahui8.sdk.logging.event;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/15
 */
public class LogEvent {
    LogSchema schema;
    Level level;
    Logger logger;
    Throwable throwable;
    long ts;

    {
        ts = System.currentTimeMillis();
    }

    public LogEvent(LogSchema schema, Level level, Logger logger) {
        this.schema = schema;
        this.level = level;
        this.logger = logger;
    }

    public LogEvent(LogSchema schema, Level level, Logger logger, Throwable throwable) {
        this.schema = schema;
        this.level = level;
        this.logger = logger;
        this.throwable = throwable;
    }

    public LogSchema getSchema() {
        return schema;
    }

    public void setSchema(LogSchema schema) {
        this.schema = schema;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public long getTs() {
        return ts;
    }
}
