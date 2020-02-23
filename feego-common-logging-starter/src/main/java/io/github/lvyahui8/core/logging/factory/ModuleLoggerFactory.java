package io.github.lvyahui8.core.logging.factory;

import org.slf4j.ILoggerFactory;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 13:01
 */
public interface ModuleLoggerFactory {
    org.slf4j.Logger getLogger(String pattern, String loggerName, ILoggerFactory loggerFactory, String fileName, String fileNamePattern);
}
