package io.github.lvyahui8.sdk.utils;

import java.util.concurrent.Executor;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/18 21:51
 */
public class AsyncTaskExecutorInitializer {
    public static void initAsyncTaskExecutor(Executor executor) {
        if (AsyncTaskExecutor.getExecutor() != null) {
            throw new UnsupportedOperationException("The asynchronous task executor can only be initialized once.");
        }
        AsyncTaskExecutor.setExecutor(executor);
    }
}
