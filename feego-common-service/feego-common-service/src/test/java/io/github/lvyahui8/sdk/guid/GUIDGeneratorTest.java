package io.github.lvyahui8.sdk.guid;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import io.github.lvyahui8.sdk.utils.SystemUtils;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/2
 */
public class GUIDGeneratorTest {

    static
    class Worker extends Thread {
        CountDownLatch latch ;
        long target ;
        public Worker(CountDownLatch latch,long target) {
            this.latch = latch;
            this.target = target;
        }

        @Override
        public void run() {
            int i = 0;
            try {
                while(i++ < target) {
                    GUIDGenerator.createStringTypeGUID();
                }
            } finally {
                latch.countDown();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateStringTypeGUIDBasic() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(GUIDGenerator.createStringTypeGUID());
        }
        int n = Runtime.getRuntime().availableProcessors() * 3;
        CountDownLatch latch = new CountDownLatch(n);
        long target = 10000000;

    }

    @Test
    public void testCreateStringTypeGUIDPerformance() throws Exception {
        int n = Runtime.getRuntime().availableProcessors() * 3;
        CountDownLatch latch = new CountDownLatch(n);
        long target = 10000000;
        long begin = System.currentTimeMillis();
        // 单线程 1千万
        for (int i = 0; i < target * 10; i++) {
            GUIDGenerator.createStringTypeGUID();
        }
        System.out.println("cost time:" + (System.currentTimeMillis() - begin) + " ms");
        // 多线程
        begin = System.currentTimeMillis();
        for (int i = 0 ; i < n ; i++) {
            Worker worker = new Worker(latch,target);
            worker.start();
        }
        latch.await();
        System.out.println("cost time:" + (System.currentTimeMillis() - begin) + " ms");
    }
}