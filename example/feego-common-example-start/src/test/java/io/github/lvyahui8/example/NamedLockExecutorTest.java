package io.github.lvyahui8.example;

import io.github.lvyahui8.sdk.lock.NamedLock;
import io.github.lvyahui8.sdk.lock.NamedLockExecutor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/2/3
 */
public class NamedLockExecutorTest {
    @Data
    class Resource {
        int state = 0;
    }
    @Test
    public void testExec() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final Resource resource = new Resource();
        final AtomicBoolean flag = new AtomicBoolean(true);
        final AtomicInteger reloadCount = new AtomicInteger(0);
        final AtomicLong accessCount = new AtomicLong(0);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                while(flag.get()) {
                    try {
                        Resource k = NamedLockExecutor.exec("" + (101 % 100),() -> {
                            // 访问资源
                            /// System.out.println("state=" + resource.state);
                            accessCount.incrementAndGet();
                            return resource.state <= 0 ? null : resource;
                        }  ,() -> {
                            // 加载资源
                            resource.state += 1;

                            System.out.println("reloaded:" + reloadCount.incrementAndGet());
                            return resource;
                        });
                        Assert.assertEquals(k.state,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            // 将资源失效掉 (过期)
            resource.state = 0;
        }
        flag.set(false);
        service.shutdownNow();
        System.out.printf("access cnt:%d,reload cnt:%d, cost:%dms%n",
                accessCount.get(),
                reloadCount.get(),
                System.currentTimeMillis() - begin);
    }
}
