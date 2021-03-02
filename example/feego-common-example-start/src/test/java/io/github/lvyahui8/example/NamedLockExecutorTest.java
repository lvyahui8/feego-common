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

        Resource resource = new Resource();
        AtomicBoolean flag = new AtomicBoolean(true);
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                while(flag.get()) {
                    try {
                        Resource k = NamedLockExecutor.exec(new String("" + (101 % 100)),() -> {
                            // 访问资源
                            // System.out.println("access");
                            return resource.state <= 0 ? null : resource;
                        }  ,() -> {
                            // 加载资源
                            resource.state += 1;
                            System.out.println("reloaded");
                            return resource;
                        });
                        Assert.assertEquals(k.state,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            // 将资源失效掉 (过期)
            resource.state = 0;
        }
        flag.set(false);
        service.shutdownNow();
    }
}
