package io.github.lvyahui8.sdk.guid;

import io.github.lvyahui8.sdk.utils.SystemUtils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 *
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
public class GUIDGenerator {

    private static final long IP_BITS  = getIpBits();

    private static final String HEX_IPV4_ADDR = getHexIpV4Addr();

    private static final String RANDOM_SEED = getHexRandomSeed();

    private static final long STR_GUID_MAX_SEQUENCE = 0xFFFFF;

    private static final long STR_GUID_MIN_SEQUENCE = 0x10000;

    private static final AtomicLong stringGuidCounter = new AtomicLong(STR_GUID_MIN_SEQUENCE);

    private static final AtomicInteger longGuidCounter = new AtomicInteger(0);

    private static String getHexRandomSeed() {
        Random r = new Random(System.nanoTime());
        return Integer.toHexString( r.nextInt(0xEFF)  + 0x100);
    }

    private static String getHexIpV4Addr() {
        String hexIpAddr = Long.toHexString(IP_BITS);
        if (hexIpAddr.length() != 8) {
            return "0" + hexIpAddr;
        }
        return hexIpAddr;
    }

    private static long getIpBits() {
        long ipBits = 0;
        String hostAddress = SystemUtils.getLocalAddress().getHostAddress();
        String [] items = hostAddress.split("\\.");
        for (int i = 0; i < items.length; i++) {
            ipBits = ipBits | (Long.parseLong(items[i]) << (24 - (i << 3)));
        }
        return ipBits;
    }

    private static String hexCycleSequence() {
        while(true) {
            long current = stringGuidCounter.get();
            long update = current + 1;
            if (update > STR_GUID_MAX_SEQUENCE) {
                update = STR_GUID_MIN_SEQUENCE;
            }
            if (stringGuidCounter.compareAndSet(current,update)) {
                return Long.toHexString(current);
            }
        }
    }

    private static int cycleSequence(int maxValue) {
        while(true) {
            int current = longGuidCounter.get();
            int update = current + 1;
            if (update > maxValue) {
                update = 0;
            }
            if (longGuidCounter.compareAndSet(current,update)) {
                return current;
            }
        }
    }

    public static String createStringTypeGUID() {
        return HEX_IPV4_ADDR + RANDOM_SEED + Long.toHexString(System.currentTimeMillis()) + hexCycleSequence();
    }

    public static long createLongTypeGUID(){
        return createLongTypeGUID(32,1);
    }

    public static long createLongTypeGUID(int ipDigits) {
        return createLongTypeGUID(ipDigits,33 - ipDigits);
    }

    public static long createLongTypeGUID(int ipDigits,int seqDigits) {
        if (ipDigits <= 0 || seqDigits <=0 ) {
            throw new IllegalArgumentException("param > 0");
        }
        if (ipDigits > 32) {
            throw new IllegalArgumentException("ipDigits <= 32");
        }
        if (ipDigits + seqDigits != 33) {
            throw new IllegalArgumentException("ipDigits + seqDigits must be equal to 33.");
        }

        return (((System.currentTimeMillis() >> 10) & 0x3FFFFFFF) << 33)
                | ((IP_BITS & (0xFFFFFFFF >>> (32 - ipDigits))) << seqDigits)
                | cycleSequence(~(-1 << seqDigits));
    }

}
