package io.github.lvyahui8.core.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:21
 */
@SuppressWarnings("unused")
public class AsyncTaskExecutor {

    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PACKAGE)
    private static Executor executor;

    public static void submit(Runnable r) {
        executor.execute(r);
    }

    public static <T> T execute(Callable<T> c) {
        return execute(c);
    }
}
