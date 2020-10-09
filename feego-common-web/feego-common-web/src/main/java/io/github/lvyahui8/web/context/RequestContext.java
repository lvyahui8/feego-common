package io.github.lvyahui8.web.context;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
public class RequestContext {
    public static final ThreadLocal<RequestMessage> REQUEST_LOCAL = new ThreadLocal<>();

    public static void putRequest(RequestMessage message) {
        REQUEST_LOCAL.set(message);
    }

    public static String getTraceId() {
        return REQUEST_LOCAL.get().getTraceId();
    }

    public static boolean isStressTraffic() {
        return REQUEST_LOCAL.get().isStressTraffic();
    }

    public static void clean() {
        REQUEST_LOCAL.remove();
    }
}
