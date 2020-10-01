package io.github.lvyahui8.sdk.guid;

import io.github.lvyahui8.sdk.utils.AsyncTaskExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * String 类型 guid (32 * 4)： 44位时间戳 + 19位随机值 | 32位IP地址 + 31位ms内递增值
 *
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
public class GUIDGenerator {

    public static final long IP_OPPOSITE_SHIFT = getLocalIpBits()  << 31;

    public static final Long RANDOM_SEED = getRandomSeed();

    public static final int RANDOM_SEED_BITS = 19;

    public static AtomicInteger count = new AtomicInteger(0);

    public static long latestTimestamp = System.currentTimeMillis();

    private static long getRandomSeed() {
        Random r = new Random(System.nanoTime());
        return r.nextInt(~(-1 << RANDOM_SEED_BITS));
    }

    private static long getLocalIpBits() {
        try {
            long ipBits = 0;
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String[] items = hostAddress.split("\\.");
            for (int i = 0; i < items.length; i++) {
                ipBits = ipBits | (Long.parseLong(items[i]) << (i << 3));
            }
            return ipBits;
        } catch (UnknownHostException e) {
            return 0;
        }
    }

    public static String createGUID() {
        long now = System.currentTimeMillis();
        long seq ;
        if (now == latestTimestamp) {
            seq = count.incrementAndGet();
        } else {
            count.set(0);
            seq = 0;
            latestTimestamp = now;
        }
        long be = System.currentTimeMillis() << RANDOM_SEED_BITS | RANDOM_SEED;
        long se = IP_OPPOSITE_SHIFT |  seq;
        long hh = 1L << 63;
        return Long.toHexString(hh | (be & (hh -1))) + Long.toHexString(hh | (se & (hh -1)));
    }

    public static void main(String[] args) {
        System.out.println(Long.toHexString(IP_OPPOSITE_SHIFT));
        System.out.println(Long.toBinaryString(IP_OPPOSITE_SHIFT));
        System.out.println(Long.toBinaryString(RANDOM_SEED));
        System.out.println(Long.toBinaryString(0x80000000));
        ExecutorService pool = Executors.newFixedThreadPool(16);
        for (int i = 0 ; i < 10000000; i ++) {
            pool.submit(() -> {
                System.out.println(createGUID());
            });
        }

    }
}
