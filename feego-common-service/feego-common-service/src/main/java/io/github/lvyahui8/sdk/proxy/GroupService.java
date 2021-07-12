package io.github.lvyahui8.sdk.proxy;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/7/12
 */

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface GroupService {
    Class<? extends DecisionMaker> value();
}
