package io.github.lvyahui8.sdk.logging.factory;

import io.github.lvyahui8.sdk.logging.logger.ModuleLogger;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 13:01
 */
public interface ModuleLoggerFactory {
    void initModuleLogger(Class<? extends ModuleLogger> ... moduleEnumClasses);
}
