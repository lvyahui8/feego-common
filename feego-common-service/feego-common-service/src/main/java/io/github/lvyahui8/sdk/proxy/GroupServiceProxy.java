package io.github.lvyahui8.sdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/7/12
 */
public class GroupServiceProxy implements InvocationHandler {
    DecisionMaker decisionMaker;
    Map<String,Object> targetMap ;
    Type groupItf;

    public GroupServiceProxy(DecisionMaker decisionMaker, Map<String, Object> targetMap, Type groupItf) {
        this.decisionMaker = decisionMaker;
        this.targetMap = targetMap;
        this.groupItf = groupItf;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String targetName = decisionMaker.decide(groupItf, method, args);
        return method.invoke(targetMap.get(targetName),args);
    }
}

