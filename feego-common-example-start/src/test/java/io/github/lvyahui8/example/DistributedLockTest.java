package io.github.lvyahui8.example;

import io.github.lvyahui8.core.lock.DistributedLock;
import io.github.lvyahui8.core.lock.LockFactory;
import io.github.lvyahui8.core.utils.AsyncTaskExecutor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 22:33
 */
public class DistributedLockTest extends BasicTest {

    @Autowired
    LockFactory lockFactory;

    @Test
    public void testTryLock() throws Exception {
        final int n = 1000  ;
        final CountDownLatch latch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            final int k = i ;
            AsyncTaskExecutor.submit(() -> {
                try {
                    DistributedLock lock = lockFactory.newDistributeLock("uid:12345",k);
                    try {
                        if (lock.tryLock()) {
                            System.out.println("process " + Thread.currentThread().getName() + " getLock");
                            Thread.sleep(60);
                        }
                    } finally {
                        lock.unlock();
                    }
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

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
    }

    @Test
    public void testTryLockWithTimeout() throws Exception {
        String key = "uid:124234";
        Random r = new Random(System.currentTimeMillis());
        DistributedLock processALock = lockFactory.newDistributeLock(key, System.nanoTime() + r.nextInt(1000));
        DistributedLock processBLock = lockFactory.newDistributeLock(key, System.nanoTime() + r.nextInt(1000) + 1000);
        StringBuilder sb  = new StringBuilder();
        try {
            processALock.lock();
            /* threadB(模拟processBLock)在processALock没释放之前， 永远拿不到锁 */
            Thread threadB = new Thread(() -> {
                try {
                    if(processBLock.tryLock(4000L, TimeUnit.MILLISECONDS)) {
                        sb.append("locked");
                        processBLock.unlock();
                    } else {
                        sb.append("timeout");
                    }
                } catch (InterruptedException ignored) {
                }
            });
            threadB.start();
            threadB.join();
        } finally {
            processALock.unlock();
        }
        Assert.assertEquals("The lock acquired by process B will definitely time out","timeout",sb.toString());
    }

    @Test
    public void testLockInterruptibly() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final String key = "uid:23423454";
        DistributedLock mainProcessLock = lockFactory.newDistributeLock(key, 815);
        try {
            mainProcessLock.lock();
            Thread thread = new Thread(() -> {
                DistributedLock lock = lockFactory.newDistributeLock(key, 623);
                boolean interrupted = false;
                latch.countDown();
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    interrupted = true;
                } finally {
                    lock.unlock();
                }
                Assert.assertTrue(interrupted);
            });
            thread.start();
            /* 等待子线程进入拿锁循环 */
            latch.await();
            thread.interrupt();
            thread.join();
        } finally {
            mainProcessLock.unlock();
        }

    }

    @Test
    public void testReentry() throws Exception {
        final DistributedLock lock = lockFactory.newDistributeLock("uid:234454", UUID.randomUUID().toString());

        int n = 10;
        for (int i = 0 ; i < n ; i ++) {
            lock.lock();
        }

        for (int i = 0 ; i < n ; i ++) {
            lock.unlock();
        }

        Function<Integer,Object> func =  new Function<Integer,Object>(){
            DistributedLock lock;

            Function<Integer,Object> setLock(DistributedLock lock) {
                this.lock = lock;
                return this;
            }

            @Override
            public Object apply(Integer i) {
                try {
                    lock.lock();
                    if (i < 100) {
                        return i + (Integer) apply(i + 1);
                    } else {
                        return i;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }.setLock(lock);

        System.out.println(func.apply(1));
    }
}
