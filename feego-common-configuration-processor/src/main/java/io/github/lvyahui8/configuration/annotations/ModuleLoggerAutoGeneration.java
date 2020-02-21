package io.github.lvyahui8.configuration.annotations;

import java.lang.annotation.*;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/21 20:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface ModuleLoggerAutoGeneration {
    String [] value() default {};
}
