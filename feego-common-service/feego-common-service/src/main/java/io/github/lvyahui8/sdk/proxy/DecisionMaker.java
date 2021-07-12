package io.github.lvyahui8.sdk.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 实现类必须具备无参构造函数
 *
 * @author feego lvyahui8@gmail.com
 * @date 2021/7/12
 */
public interface DecisionMaker {
    String decide(Type itf, Method method,Object [] methodArgs);
}
