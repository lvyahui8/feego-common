package io.github.lvyahui8.web.code;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:48
 */
public class CodeRepository {
    private static Map<Object,C> cMap = new ConcurrentHashMap<>();

    public static C get(Object object) {
        if (cMap.containsKey(object)) {
            return cMap.get(object);
        }
        try {
            C c = object.getClass().getField(((Enum<?>) object).name()).getAnnotation(C.class);
            cMap.put(object,c);
            return c;
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
