package io.github.lvyahui8.core.properties;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/19 21:18
 */
@Data
public class LoggingProperties {
    /**
     * 是否开启模块化日志
     */
    boolean      open = true;
    /**
     * 存储路径
     */
    String       storagePath;
    /**
     * 日志文件切片大小
     */
    String       maxFileSize    = "100MB";
    /**
     * 日志文件允许的最大大小
     */
    String       totalSizeCap   = "20GB";
    /**
     * 允许存多少天历史的日志
     */
    Integer      maxHistory     = 14;
    /**
     * 日志kv内容分隔符
     */
    String       fieldSeparator = "|#|";
}
