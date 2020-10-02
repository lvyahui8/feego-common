package io.github.lvyahui8.sdk.guid;

import io.github.lvyahui8.sdk.utils.SystemUtils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 *
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
public class GUIDGenerator {

    private static final String HEX_IPV4_ADDR = getHexIpV4Addr();

    private static final String RANDOM_SEED = getHexRandomSeed();

    private static final long MAX_SEQUENCE = 0xEFFFF;

    private static final long MIN_SEQUENCE = 0x10000;

    private static final AtomicLong counter = new AtomicLong(MIN_SEQUENCE);

    private static String getHexRandomSeed() {
        Random r = new Random(System.nanoTime());
        return Integer.toHexString( r.nextInt(0xEFF)  + 0x100);
    }

    private static String getHexIpV4Addr() {
        long ipBits = 0;
        String hostAddress = SystemUtils.getLocalAddress().getHostAddress();
        String[] items = hostAddress.split("\\.");
        for (int i = 0; i < items.length; i++) {
            ipBits = ipBits | (Long.parseLong(items[i]) << (24 - (i << 3)));
        }
        String hexIpAddr = Long.toHexString(ipBits);
        if (hexIpAddr.length() != 8) {
            return "0" + hexIpAddr;
        }
        return hexIpAddr;
    }

    private static String hexCycleSequence() {
        while(true) {
            long current = counter.get();
            long update = current + 1;
            if (update > MAX_SEQUENCE) {
                update = MIN_SEQUENCE;
            }
            if (counter.compareAndSet(current,update)) {
                return Long.toHexString(current);
            }
        }
    }

    public static String createStringTypeGUID() {
        return HEX_IPV4_ADDR + RANDOM_SEED + Long.toHexString(System.currentTimeMillis()) + hexCycleSequence();
    }

}
