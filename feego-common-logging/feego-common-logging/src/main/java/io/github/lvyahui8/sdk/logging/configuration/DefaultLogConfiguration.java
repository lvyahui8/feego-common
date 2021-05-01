package io.github.lvyahui8.sdk.logging.configuration;

import io.github.lvyahui8.sdk.logging.handler.AbstractLogHandler;
import io.github.lvyahui8.sdk.logging.handler.LogHandler;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/20
 */
public class DefaultLogConfiguration {
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
    /**
     * 日志格式
     */
    String       pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5level %m%n";
    /**
     * monitor 日志格式。去掉了日志级别。
     */
    String       monitorLogPattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %m%n";
    /**
     * 文件名滚动规则
     */
    String      fileRollingPattern = ".%d{yyyy-MM-dd}.%i";
    /**
     * 文件存放规则
     */
    String      fileName = "$logType/$module";
    /**
     * 日志处理工具
     */
    Class<? extends AbstractLogHandler> logHandler;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public Integer getMaxHistory() {
        return maxHistory;
    }

    public void setMaxHistory(Integer maxHistory) {
        this.maxHistory = maxHistory;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getTotalSizeCap() {
        return totalSizeCap;
    }

    public void setTotalSizeCap(String totalSizeCap) {
        this.totalSizeCap = totalSizeCap;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public String getGeneralLogPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMonitorLogPattern() {
        return monitorLogPattern;
    }

    public void setMonitorLogPattern(String monitorLogPattern) {
        this.monitorLogPattern = monitorLogPattern;
    }

    public String getFileRollingPattern() {
        return fileRollingPattern;
    }

    public void setFileRollingPattern(String fileRollingPattern) {
        this.fileRollingPattern = fileRollingPattern;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Class<? extends AbstractLogHandler> getLogHandler() {
        return logHandler;
    }

    public void setLogHandler(Class<? extends AbstractLogHandler> logHandler) {
        this.logHandler = logHandler;
    }
}
