package io.github.lvyahui8.example;

import io.github.lvyahui8.sdk.lock.NamedLock;
import io.github.lvyahui8.sdk.lock.NamedLockExecutor;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/2/3
 */
public class NamedLockExecutorTest {
    @Test
    public void testExec() throws Exception {
        List<Integer> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                while(true) {
                    try {
                        int k = NamedLockExecutor.exec("1",() -> list.isEmpty() ? null : list.get(0),() -> {
                            int a = 1;
                            list.add(a);
                            return a;
                        });
                        if (k != 1 || list.size() != 1) {
                            System.out.println("k = " + k + ", size:" + list.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            list.clear();
        }
        service.shutdownNow();
    }
}
