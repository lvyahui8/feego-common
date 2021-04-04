package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import org.slf4j.event.Level;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public class DefaultSchemaHandler implements SchemaHandler {
    @Override
    public Level runtimeLevel(String enumLoggerName) {
        return Level.INFO;
    }

    @Override
    public LogSchema beforeOutput(LogSchema logSchema) {
        return logSchema;
    }
}
