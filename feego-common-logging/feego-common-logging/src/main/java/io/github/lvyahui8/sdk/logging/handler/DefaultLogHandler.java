package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/1
 */
public class DefaultLogHandler extends AbstractLogHandler {

    @Override
    public LogSchema innerBeforeOutput(LogSchema logSchema) {
        return logSchema;
    }
}
