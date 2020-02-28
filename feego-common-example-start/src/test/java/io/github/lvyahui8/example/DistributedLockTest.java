package io.github.lvyahui8.example;

import io.github.lvyahui8.core.lock.DistributedLock;
import io.github.lvyahui8.core.lock.LockFactory;
import io.github.lvyahui8.core.utils.AsyncTaskExecutor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:33
 */
public class DistributedLockTest extends BasicTest {

    @Autowired
    LockFactory lockFactory;

    @Test
    public void testTryLock() throws Exception {
        DistributedLock lock = lockFactory.newDistributeLock("uid:12345", System.currentTimeMillis());

        for (int i = 0; i < 1000; i++) {
            AsyncTaskExecutor.submit(() -> {
                try {
                    try {
                        if (lock.tryLock()) {
                            System.out.println("get lock");
                            Thread.sleep(3000);
                        }
                    } finally {
                        lock.unlock();
                    }
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                }
            });
        }
        Thread.sleep(4000);
    }

    @Test
    public void testLock() throws Exception {
        String key = "uid:123456";
        DistributedLock lock = lockFactory.newDistributeLock(key, System.nanoTime());
        Thread threadA = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("a thread locked");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("a thread unlocked");
                lock.unlock();
            }
        });

        threadA.start();

        Thread threadB = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("b thread locked");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("b thread unlocked");
                lock.unlock();
            }
        });

        threadB.start();
        threadB.join();

        lock.lock();

        Thread threadC = new Thread(() -> {
            lock.tryLock(2000, TimeUnit.MILLISECONDS);
            System.out.println("get lock timeout");
        });
        threadC.start();
        threadC.join();
    }
}
