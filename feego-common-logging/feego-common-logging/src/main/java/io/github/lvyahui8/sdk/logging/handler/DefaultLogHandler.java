package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import org.slf4j.event.Level;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/1
 */
public class DefaultLogHandler extends AbstractLogHandler {

    @Override
    public Level innerRuntimeLevel(String enumLoggerName) {
        return Level.INFO;
    }

    @Override
    public LogSchema innerBeforeOutput(LogSchema logSchema) {
        return logSchema;
    }
}
