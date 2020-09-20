package io.github.lvyahui8.sdk.logging.configuration;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/9/20
 */
public class LogbackConfiguration extends AbstractLogConfiguration {
    String       totalSizeCap;

    public String getTotalSizeCap() {
        return totalSizeCap;
    }

    public void setTotalSizeCap(String totalSizeCap) {
        this.totalSizeCap = totalSizeCap;
    }
}
