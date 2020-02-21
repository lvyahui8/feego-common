package io.github.lvyahui8.core.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 0:12
 */
public class LogSchema {
    private Map<String,Object> items = new ConcurrentSkipListMap<>();

    protected LogSchema() {
    }

    public static LogSchema empty() {
        return new LogSchema();
    }


    public LogSchema of(String key,Object value) {
        items.put(key,value);
        return this;
    }

    public Detail build() {
        Detail detail  = new Detail();
        detail.pattern = "";
        detail.args = new Object[items.size()];
        int i = 0 ;
        for (Map.Entry<String,Object> item : items.entrySet()) {
            detail.pattern += item.getKey() + ":{}|";
            detail.args[i++] = item.getValue();
        }
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
