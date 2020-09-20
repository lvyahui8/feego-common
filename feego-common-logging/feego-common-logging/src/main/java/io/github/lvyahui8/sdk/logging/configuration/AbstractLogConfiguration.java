package io.github.lvyahui8.sdk.logging.configuration;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/20
 */
public class AbstractLogConfiguration {
    String       maxFileSize;
    Integer      maxHistory;

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
}
