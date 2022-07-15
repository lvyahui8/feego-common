package io.github.lvyahui8.sdk.logging.factory;

import io.github.lvyahui8.sdk.logging.configuration.DefaultLogConfiguration;
import io.github.lvyahui8.sdk.logging.handler.LogHandler;
import io.github.lvyahui8.sdk.logging.logger.ModuleLogger;
import io.github.lvyahui8.sdk.logging.logger.ModuleLoggerRepository;
import io.github.lvyahui8.sdk.logging.logger.DefaultModuleLogger;
import io.github.lvyahui8.sdk.logging.logger.RootLogger;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/20
 */
public abstract class AbstractLoggerFactory implements ModuleLoggerFactory {

    protected DefaultLogConfiguration configuration;

    public AbstractLoggerFactory(DefaultLogConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    @SafeVarargs
    public final void initModuleLogger(Class<? extends ModuleLogger> ... moduleEnumClasses) {
        for (Class<? extends ModuleLogger> moduleEnumClass : moduleEnumClasses) {
            if (! moduleEnumClass.isEnum()) {
                continue;
                /// throw new RuntimeException("The log module class must be an enum.");
            }
            LogHandler logHandler = null;
            if (configuration.getLogHandler() != null) {
                try {
                    logHandler = configuration.getLogHandler().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create SchemaHandler",e);
                }
            }
            /* 使用代理类替换代理枚举实现 */
            for (Object enumInstance : moduleEnumClass.getEnumConstants()) {
                Enum<?> loggerEnum  = (Enum<?>) enumInstance;
                if (ModuleLoggerRepository.getModuleLogger(loggerEnum.name()) != null) {
                    // 相同模块不重复创建logger
                    continue;
                }
                if (loggerEnum.name().equals("_root")) {
                    continue;
                }
                Logger generalLogger = createSlf4jLogger(loggerEnum.name(), "general", configuration.getGeneralLogPattern());
                Logger monitorLogger = createSlf4jLogger(loggerEnum.name(), "monitor", configuration.getMonitorLogPattern());
                Logger errorLogger = generalLogger;
                if (configuration.isSeparateErrorLog()) {
                    errorLogger = createSlf4jLogger(loggerEnum.name(), "error", configuration.getMonitorLogPattern());
                }
                ModuleLogger realModuleLogger = new DefaultModuleLogger(
                        loggerEnum.name(),
                        generalLogger,
                        errorLogger,
                        monitorLogger,
                        configuration.getFieldSeparator(),
                        logHandler
                );
                ModuleLoggerRepository.put(loggerEnum.name(), realModuleLogger);
            }
            RootLogger.getInstance().setLogHandler(logHandler);
        }
    }

    private Logger createSlf4jLogger(String moduleName, String logType, String logPattern) {
        if (! configuration.getFileName().contains("$module") || ! configuration.getFileName().contains("$logType")) {
            throw new RuntimeException("Illegal file name declaration :" + configuration.getFileName());
        }
        String storagePath = (configuration.getStoragePath() == null ?
                System.getProperty("user.home") : configuration.getStoragePath()) + File.separator + "logs";

        String fileName = storagePath + File.separator +
                configuration.getFileName().replaceAll("\\$module", moduleName).replaceAll("\\$logType",logType) + ".log";
        String fileNamePattern = fileName + configuration.getFileRollingPattern();

        File file = new File(fileName);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new RuntimeException("No permission to create log path!");
        }

        return createSlf4jLogger0(logPattern, moduleName + '-' + logType, LoggerFactory.getILoggerFactory(),
                fileName, fileNamePattern);
    }

    abstract org.slf4j.Logger createSlf4jLogger0(String pattern, String loggerName, ILoggerFactory loggerFactory, String fileName, String fileNamePattern);
}
