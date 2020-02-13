package io.github.lvyahui8.core.properties;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/12 18:17
 */
@Data
public class ExecutorProperties {
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 7;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 2147483647;
    private boolean allowCoreThreadTimeOut = false;
}
