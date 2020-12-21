package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/9
 */
public interface SchemaHandler {
    LogSchema beforeOutput(LogSchema logSchema);
}
