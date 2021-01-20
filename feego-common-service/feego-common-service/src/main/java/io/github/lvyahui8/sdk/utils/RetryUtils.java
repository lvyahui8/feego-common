package io.github.lvyahui8.sdk.utils;

import java.util.concurrent.Callable;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/20
 */
public abstract class RetryUtils {
    public static <RET_TYPE> RET_TYPE retryGet(Callable<RET_TYPE> func,int retryNum) throws Exception {
        while(retryNum-- > 0) {
            try {
                return func.call();
            } catch (Exception e) {
                if (retryNum == 0) {
                    throw e;
                }
            }
        }
        //  不可达
        throw new IllegalArgumentException("retryNum must be greater than 0");
    }

    public static void retryDo(Runnable func,int retryNum) throws Exception {
        retryGet(() -> {func.run(); return null;},retryNum);
    }
}
