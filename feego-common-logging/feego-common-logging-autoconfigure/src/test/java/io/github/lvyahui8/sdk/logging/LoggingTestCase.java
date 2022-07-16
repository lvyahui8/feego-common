package io.github.lvyahui8.sdk.logging;

import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import org.junit.Test;

public class LoggingTestCase extends BaseTest {
    @Test
    public void testRootLogger() {
        TestLogger._root.info("hello");
        TestLogger._root.info(LogSchema.biz("hello").of("suc",true).of("cost",1));
    }
}
