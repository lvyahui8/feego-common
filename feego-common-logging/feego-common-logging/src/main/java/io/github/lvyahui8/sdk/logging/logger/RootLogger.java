package io.github.lvyahui8.sdk.logging.logger;

import io.github.lvyahui8.sdk.logging.configuration.LogConstants;
import io.github.lvyahui8.sdk.logging.handler.DefaultLogHandler;
import io.github.lvyahui8.sdk.logging.handler.LogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/1
 */
public class RootLogger extends DefaultModuleLogger {
    private RootLogger(String enumLoggerName, Logger logger, Logger monitorLogger,
                       String fieldSeparator, LogHandler logHandler) {
        super(enumLoggerName, logger, monitorLogger, fieldSeparator, logHandler);
    }

    private static RootLogger instance ;

    public static RootLogger getInstance() {
        if (instance == null) {
            Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            instance =  new RootLogger("$rootLogger",
                    logger,logger, LogConstants.Config.FIELD_SP,new DefaultLogHandler());
        }
        return instance;
    }
}
