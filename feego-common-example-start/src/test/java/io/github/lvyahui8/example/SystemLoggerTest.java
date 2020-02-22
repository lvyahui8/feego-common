package io.github.lvyahui8.example;

import feego.common.io.github.lvyahui8.example.SystemLogger;
import io.github.lvyahui8.core.logging.LogSchema;
import io.github.lvyahui8.core.utils.AsyncTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 0:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SystemLoggerTest {

    @Test
    public void testBasicFunction() throws Exception {
        SystemLogger.status.info(LogSchema.empty().of("name","おおはしみく"));
    }

    @Test
    public void testBenchmark() throws Exception {
        int n = Runtime.getRuntime().availableProcessors();
        CountDownLatch latch = new CountDownLatch(n);
        for (int i = 0 ; i < n; i ++) {
            AsyncTaskExecutor.submit(() -> {
               try {
                   for (int j = 0 ; j < 10000000 ; j ++) {
                       SystemLogger.status.info(LogSchema.empty().of("name","feego").of("age",26));
                   }
               } finally {
                   latch.countDown();
               }
            });
        }
        latch.await();
    }
}
