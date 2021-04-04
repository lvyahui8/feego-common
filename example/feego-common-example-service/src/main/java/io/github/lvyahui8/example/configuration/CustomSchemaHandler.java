package io.github.lvyahui8.example.configuration;

import io.github.lvyahui8.sdk.logging.handler.DefaultSchemaHandler;
import io.github.lvyahui8.sdk.logging.handler.SchemaHandler;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import io.github.lvyahui8.web.context.RequestContext;
import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public class CustomSchemaHandler extends DefaultSchemaHandler {

    /**
     * 可以结合一些运行时配置系统，使得level运行时可以动态调整
     */
    public static final Map<CustomLogger,Level> levelMap = new HashMap<>();
    static  {
        levelMap.put(CustomLogger.uc,Level.ERROR);
    }

    @Override
    public Level runtimeLevel(String enumLoggerName) {
        CustomLogger customLogger = null;
        try {
            customLogger = CustomLogger.valueOf(enumLoggerName);
        } catch (Exception ignored) {}
        if (customLogger != null) {
            return levelMap.containsKey(customLogger) ?
                    levelMap.get(customLogger) : super.runtimeLevel(enumLoggerName);
        }
        return super.runtimeLevel(enumLoggerName);
    }

    @Override
    public LogSchema beforeOutput(LogSchema logSchema) {
        logSchema.prepend("tid",Thread.currentThread().getId())
                .prepend("rid", RequestContext.getTraceId());
        return logSchema;
    }
}
