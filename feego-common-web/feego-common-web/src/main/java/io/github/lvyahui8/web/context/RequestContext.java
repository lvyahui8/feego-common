package io.github.lvyahui8.web.context;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
public class RequestContext {
    public static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static void clean() {
        TRACE_ID.remove();
    }
}
