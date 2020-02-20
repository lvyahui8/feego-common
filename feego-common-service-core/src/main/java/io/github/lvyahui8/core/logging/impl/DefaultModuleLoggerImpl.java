package io.github.lvyahui8.core.logging.impl;

import io.github.lvyahui8.core.logging.ModuleLogger;
import org.slf4j.Logger;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:27
 */
public class DefaultModuleLoggerImpl implements ModuleLogger {
    Logger logger;

    public DefaultModuleLoggerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String msg) {
        logger.info("msg:{}",msg);
    }
}
