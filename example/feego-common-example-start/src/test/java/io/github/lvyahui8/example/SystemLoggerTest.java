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
        SystemLogger.campaign.info(
                LogSchema.empty().of("id",1).of("begin",20200307).of("end",20200310).of("status",1)
        );
        SystemLogger.campaign.monitor(
                LogSchema.biz("createOrder").of("orderId","234324324234234").of("amount",19999)
        );
    }

    @Test
    public void testBenchmark() throws Exception {
        int n = Runtime.getRuntime().availableProcessors();
        CountDownLatch latch = new CountDownLatch(n);
        for (int i = 0 ; i < n; i ++) {
            AsyncTaskExecutor.submit(() -> {
               try {
                   for (int j = 0 ; j < 10000000 ; j ++) {
                       SystemLogger.campaign.info(LogSchema.empty().of("name","feego").of("age",26));
                   }
               } finally {
                   latch.countDown();
               }
            });
        }
        latch.await();
    }

    @Test
    public void testError() throws Exception {
        SystemLogger.campaign.error("fk u {}","dq");
        SystemLogger.campaign.error(LogSchema.empty().of("x",System.nanoTime()));
        SystemLogger.campaign.error(LogSchema.empty().of("t",System.currentTimeMillis()),new RuntimeException("unknown exception!"));
        SystemLogger.campaign.error(LogSchema.empty().of("t",System.currentTimeMillis()).of("d",20200309),new RuntimeException("unknown exception!"));
    }


    @Test
    public void testWarn() throws Exception {
        SystemLogger.campaign.warn("fk x {}","mm");
        SystemLogger.campaign.warn("issue rebate",new RuntimeException("system busy"));
        SystemLogger.campaign.warn(LogSchema.biz("rebate").of("t",System.currentTimeMillis()),new RuntimeException("system busy"));
        SystemLogger.campaign.warn(LogSchema.empty().of("x",System.nanoTime()),new RuntimeException("system busy"));
    }
}
