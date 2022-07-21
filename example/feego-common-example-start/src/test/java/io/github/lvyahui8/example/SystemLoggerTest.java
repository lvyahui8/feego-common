package io.github.lvyahui8.example;

import feego.common.io.github.lvyahui8.example.SystemLogger;
import io.github.lvyahui8.example.configuration.CustomLogger;
import io.github.lvyahui8.sdk.guid.GUIDGenerator;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;
import io.github.lvyahui8.sdk.utils.AsyncTaskExecutor;
import io.github.lvyahui8.web.context.RequestContext;
import io.github.lvyahui8.web.context.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.event.Level;
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
        RequestMessage message = new RequestMessage();
        message.setTraceId(GUIDGenerator.createStringTypeGUID());
        RequestContext.putRequest(message);

        SystemLogger.campaign.info(
                LogSchema.empty().of("id",1).of("begin",20200307).of("end",20200310).of("status",1)
        );
        SystemLogger.campaign.monitor(
                LogSchema.biz("createOrder").of("orderId","234324324234234").of("amount",19999)
        );
        CustomLogger.uc.monitor(LogSchema.empty().of("c",1));
        SystemLogger.campaign.trace("trace closed");
        SystemLogger.campaign.trace("hello");
        SystemLogger.campaign.trace("hello {}","dj");
        SystemLogger.campaign.trace("hello {} {}","d,","j");
        SystemLogger.campaign.trace("hello {} {} {} {}",'d','j','b','j');

        SystemLogger.campaign.debug(LogSchema.biz("qrcode-pay").of("orderId",1234).of("amount",100).of("suc",'Y'));
        SystemLogger.campaign.debug("hello");
        SystemLogger.campaign.debug("hello {}","dj");
        SystemLogger.campaign.debug("hello {} {}","d,","j");
        SystemLogger.campaign.debug("hello {} {} {} {}",'d','j','b','j');

        SystemLogger.campaign.info("hello");
        SystemLogger.campaign.info("hello {}","dj");
        SystemLogger.campaign.info("hello {} {}","d,","j");
        SystemLogger.campaign.info("hello {} {} {} {}",'d','j','b','j');

        SystemLogger.campaign.warn("hello");
        SystemLogger.campaign.warn("hello {}","dj");
        SystemLogger.campaign.warn("hello {} {}","d,","j");
        SystemLogger.campaign.warn("hello {} {} {} {}",'d','j','b','j');
        SystemLogger.campaign.warn("exp",new RuntimeException("fk"));

        SystemLogger.campaign.error("hello");
        SystemLogger.campaign.error("hello {}","dj");
        SystemLogger.campaign.error("hello {} {}","d,","j");
        SystemLogger.campaign.error("hello {} {} {} {}",'d','j','b','j');
        SystemLogger.campaign.error("exp",new RuntimeException("fk"));

    }

    @Test
    public void testBenchmark() throws Exception {
        int n = Runtime.getRuntime().availableProcessors();
        CountDownLatch latch = new CountDownLatch(n);
        for (int i = 0 ; i < n; i ++) {
            AsyncTaskExecutor.execute(() -> {
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

    @Test
    public void testMonitor() throws Exception {
        SystemLogger.campaign.monitor(LogSchema.biz("apiM").of("api","/post/create").suc(true).cost(100));
    }
}
