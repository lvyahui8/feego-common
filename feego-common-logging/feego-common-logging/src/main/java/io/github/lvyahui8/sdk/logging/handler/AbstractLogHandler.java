package io.github.lvyahui8.sdk.logging.handler;

import io.github.lvyahui8.sdk.logging.configuration.LogConstants;
import io.github.lvyahui8.sdk.logging.context.LogContext;
import io.github.lvyahui8.sdk.logging.context.LogContextHolder;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public abstract class AbstractLogHandler implements LogHandler {
    @Override
    public final Level runtimeLevel(String enumLoggerName) {
        Level level = LogContextHolder.getLevel();
        if (level != null) {
            return level;
        }
        return innerRuntimeLevel(enumLoggerName);
    }

    public abstract Level innerRuntimeLevel(String enumLoggerName);

    @Override
    public final LogSchema beforeOutput(LogSchema logSchema) {
        LogContext logContext = LogContextHolder.getLogContext();
        if (logContext != null) {
            if (StringUtils.isNotBlank(logContext.getBiz())) {
                logSchema.of(LogConstants.ReversedKey.BUSINESS,logContext.getBiz());
            }
            if (StringUtils.isNotBlank(logContext.getTid())) {
                logSchema.of(LogConstants.ReversedKey.TRACE_ID,logContext.getTid());
            }
        }
        return innerBeforeOutput(logSchema);
    }

    public abstract LogSchema innerBeforeOutput(LogSchema logSchema);
}