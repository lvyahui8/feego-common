package io.github.lvyahui8.sdk.logging.schema;

import org.apache.commons.collections4.map.ListOrderedMap;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 0:12
 */
public class LogSchema {
    /// private Map<String,Object> items = Collections.synchronizedMap(new ListOrderedMap<>());
    private ListOrderedMap<String,Object> items = new ListOrderedMap<>();

    private LogSchema() {
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

    public LogSchema clear() {
        items.clear();
        return this;
    }

    public Detail buildDetail(String sp) {
        return buildDetail(sp,false);
    }

    public Detail buildDetail(String sp,boolean reserved) {
        Detail detail  = new Detail();
        detail.args = new Object[reserved ? items.size() + 1 : items.size()];
        StringBuilder sb = new StringBuilder();
        int i = 0 ;
        for (Map.Entry<String,Object> item : items.entrySet()) {
            sb.append(item.getKey()).append(":{}").append(sp);
            detail.args[i++] = item.getValue();
        }
        detail.pattern = sb.toString();
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
