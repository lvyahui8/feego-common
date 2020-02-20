package io.github.lvyahui8.core.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/20 22:30
 */
public class ModuleLoggerInvocationHandler
    implements InvocationHandler {

    private ModuleLogger moduleLogger;

    public ModuleLoggerInvocationHandler(ModuleLogger moduleLogger) {
        this.moduleLogger = moduleLogger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(moduleLogger,args);
    }
}
