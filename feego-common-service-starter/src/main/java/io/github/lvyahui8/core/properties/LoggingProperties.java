package io.github.lvyahui8.core.properties;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/19 21:18
 */
@Data
public class LoggingProperties {
    boolean open = true;
    String  maxFileSize    = "100MB";
    String  totalSizeCap   = "10GB";
    Integer maxHistory     = 14;
    String  fieldSeparator = "|#|";
}
