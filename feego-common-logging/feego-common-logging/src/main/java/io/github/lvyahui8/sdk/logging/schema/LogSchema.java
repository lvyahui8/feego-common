package io.github.lvyahui8.sdk.logging.schema;

import org.apache.commons.collections4.map.ListOrderedMap;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 0:12
 */
public final class LogSchema {
    /// private Map<String,Object> items = Collections.synchronizedMap(new ListOrderedMap<>());
    private final ListOrderedMap<String,Object> items = new ListOrderedMap<>();

    private LogSchema() {
        init();
    }

    public void init() {
    }

    public static LogSchema empty() {
        return new LogSchema();
    }

    public static LogSchema biz(String biz) {
        return empty().of("biz",biz);
    }

    public LogSchema of(String key,Object value) {
        items.put(key,value);
        return this;
    }

    public LogSchema prepend(String key,Object value) {
        items.put(0,key,value);
        return this;
    }

    public LogSchema success(boolean  suc) {
        items.put("suc",suc ? 'Y' : 'N');
        return this;
    }

    public LogSchema clear() {
        items.clear();
        return this;
    }

    public Detail buildDetail(String sp) {
        return buildDetail(sp,0);
    }

    public Detail buildDetail(String sp,int reserved) {
        Detail detail  = new Detail();
        detail.args = new Object[items.size() + reserved ];
        StringBuilder sb = new StringBuilder();
        int i = 0 ;
        for (Map.Entry<String,Object> item : items.entrySet()) {
            sb.append(item.getKey()).append(":{}").append(sp);
            detail.args[i++] = item.getValue();
        }
        detail.pattern = sb.toString();
        return detail;
    }

    public Detail buildDetail(String sp,String format,Object ... arguments) {
        LogSchema.Detail detail = this.buildDetail(sp);
        String pattern = detail.getPattern() + "msg:" + format + sp;
        Object[] args ;
        if (arguments != null && arguments.length > 0 ) {
            args = new Object[detail.getArgs().length + arguments.length];
            System.arraycopy(detail.getArgs(),0,args,0,detail.getArgs().length);
            System.arraycopy(arguments,0,args,detail.getArgs().length,arguments.length);
        } else {
            args = detail.getArgs();
        }
        detail.pattern = pattern;
        detail.args = args;
        return detail;
    }

    public static class Detail {
        String pattern;
        Object [] args;

        public Object[] getArgs() {
            return args;
        }

        public String getPattern() {
            return pattern;
        }

    }
}
