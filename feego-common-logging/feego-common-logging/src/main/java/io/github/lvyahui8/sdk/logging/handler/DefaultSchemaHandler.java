package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public class DefaultSchemaHandler implements SchemaHandler {
    @Override
    public LogSchema beforeOutput(LogSchema logSchema) {
        return logSchema;
    }
}
