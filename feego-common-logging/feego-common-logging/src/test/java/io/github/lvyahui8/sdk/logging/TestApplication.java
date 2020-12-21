package io.github.lvyahui8.sdk.logging;

import io.github.lvyahui8.sdk.logging.configuration.DefaultLogConfiguration;
import io.github.lvyahui8.sdk.logging.factory.LogbackModuleLoggerFactory;
import io.github.lvyahui8.sdk.logging.factory.ModuleLoggerFactory;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/23
 */
public class TestApplication {
    public static void main(String[] args) {
        // 只需初始化一次
        ModuleLoggerFactory factory = new LogbackModuleLoggerFactory(new DefaultLogConfiguration());
        factory.initModuleLogger(TestLogger.class);
        // 在任意地方使用日志类
        TestLogger.cache.info("hello world");
        TestLogger.db.info(LogSchema.biz("sql-trace").of("sql","select 1:").of("suc",true).of("cost",100));
        TestLogger.mq.monitor(LogSchema.biz("order-topic-consume").of("body","{}"));
    }
}
