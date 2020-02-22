package io.github.lvyahui8.core.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 0:12
 */
public class LogSchema {
    private Map<String,Object> items = new ConcurrentSkipListMap<>();

    private LogSchema() {
    }

    public static LogSchema empty() {
        return new LogSchema();
    }


    public LogSchema of(String key,Object value) {
        items.put(key,value);
        return this;
    }

    public LogSchema clear() {
        items.clear();
        return this;
    }

    public Detail build(String sp) {
        Detail detail  = new Detail();
        detail.args = new Object[items.size()];
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
