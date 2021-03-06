package io.github.lvyahui8.sdk.lock;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/2/3
 */
public class NamedLockExecutor {
    private static final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    private static final ReentrantLock [] stripes = new ReentrantLock[1024];

    static {
        for (int i = 0; i < stripes.length; i++) {
            stripes[i] = new ReentrantLock();
        }
    }

    private static Lock getKeyLock(final String key) {
        ReentrantLock lock = lockMap.get(key);
        if (lock == null) {
            ReentrantLock stripeLock = getStripeLock(key);
            stripeLock.lock();
            try {
                lock = lockMap.get(key);
                if (lock == null) {
                    lockMap.put(key,lock = new ReentrantLock());
                }
            } finally {
                stripeLock.unlock();
            }
        }
        return lock;
    }

    private static void releaseKeyLock(final String key) {
        ReentrantLock stripeLock = getStripeLock(key);
        stripeLock.lock();
        try {
            lockMap.remove(key);
        } finally {
            stripeLock.unlock();
        }
    }


    private static ReentrantLock getStripeLock(String key) {
        return stripes[key.hashCode() % stripes.length];
    }

    public static <RET> RET exec(String key, Callable<RET> accessFunc,Callable<RET> loadFunc) throws Exception {
        RET ret = accessFunc.call();
        if (ret == null) {
            ret = execWithNamedLock(key, accessFunc, loadFunc);
        }
        return ret;
    }

    public static <RET> RET execWithNamedLock(String key, Callable<RET> accessFunc, Callable<RET> loadFunc) throws Exception {
        RET ret;
        Lock lock = getKeyLock(key);
        lock.lock();
        try {
            ret = accessFunc.call();
            if (ret == null) {
                ret = loadFunc.call();
            }
        } finally {
            lock.unlock();
            releaseKeyLock(key);
        }
        return ret;
    }
}
