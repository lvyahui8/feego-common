package io.github.lvyahui8.core.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/21 23:39
 */
public class ModuleLoggerRepository {
    private static final Map<String,ModuleLogger> LOGGER_MAP = new ConcurrentHashMap<>(1);

    public static void put(String moduleName,ModuleLogger moduleLogger) {
        LOGGER_MAP.put(moduleName,moduleLogger);
    }

    public static ModuleLogger getModuleLogger(String moduleName){
        return LOGGER_MAP.get(moduleName);
    }
}
