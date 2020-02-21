package io.github.lvyahui8.core.logging.impl;

import io.github.lvyahui8.core.logging.LogSchema;
import io.github.lvyahui8.core.logging.ModuleLogger;
import org.slf4j.Logger;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:27
 */
public class DefaultModuleLoggerImpl implements ModuleLogger {
    private Logger logger;

    public DefaultModuleLoggerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger getInnerLogger() {
        return logger;
    }

    @Override
    public void info(LogSchema schema) {
        LogSchema.Detail detail = schema.build();
        getInnerLogger().info(detail.getPattern(),detail.getArgs());
    }
}
