package io.github.lvyahui8.configuration.annotations;

import java.lang.annotation.*;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/14 22:25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ResourceStrings {
    String value();
}
