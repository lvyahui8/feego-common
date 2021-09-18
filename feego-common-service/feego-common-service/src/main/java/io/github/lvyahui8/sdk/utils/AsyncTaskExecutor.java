package io.github.lvyahui8.sdk.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:21
 */
@SuppressWarnings("unused")
public class AsyncTaskExecutor {

    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PUBLIC)
    private static Executor executor;

    public static void execute(Runnable r) {
        executor.execute(r);
    }

    public static <T> Future<T> submit(Callable<T> c) {
        if (executor instanceof org.springframework.core.task.AsyncTaskExecutor) {
            return ((org.springframework.core.task.AsyncTaskExecutor) executor).submit(c);
        } else if (executor instanceof ExecutorService) {
            return ((ExecutorService) executor).submit(c);
        } else {
            throw new UnsupportedOperationException("Tasks that return data in the future are not supported.");
        }
    }
}
