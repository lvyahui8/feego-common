package io.github.lvyahui8.example.configuration;

import io.github.lvyahui8.sdk.logging.handler.SchemaHandler;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import io.github.lvyahui8.web.context.RequestContext;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public class CustomSchemaHandler implements SchemaHandler {
    @Override
    public LogSchema beforeOutput(LogSchema logSchema) {
        logSchema.prepend("tid", RequestContext.getTraceId());
        return logSchema;
    }
}
