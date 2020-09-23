package io.github.lvyahui8.sdk.logging;

import io.github.lvyahui8.sdk.logging.configuration.DefaultLogConfiguration;
import io.github.lvyahui8.sdk.logging.factory.LogbackModuleLoggerFactory;
import io.github.lvyahui8.sdk.logging.schema.LogSchema;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/23
 */
public class RepeatModuleTestApplication {
    public static void main(String[] args) {
        LogbackModuleLoggerFactory factory = new LogbackModuleLoggerFactory(new DefaultLogConfiguration());
        factory.initModuleLogger(TestLogger.class,TestLogger2.class);
        TestLogger.db.info("from testLogger");
        TestLogger2.db.info("from testLogger2");
    }
}
