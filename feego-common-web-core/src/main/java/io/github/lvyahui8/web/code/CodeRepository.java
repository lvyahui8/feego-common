package io.github.lvyahui8.web.code;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:48
 */
class CodeRepository {
    private static final Map<Object,Code> CODE_MAP = new ConcurrentHashMap<>();

    private static final String LEFT_PAD = System.getProperty("feego.common.msgCode.leftPad", "5");

    static Code get(Object object) {
        if (CODE_MAP.containsKey(object)) {
            return CODE_MAP.get(object);
        }
        try {
            Enum<?> codeEnum = (Enum<?>) object;
            CodePrefix codePrefix = object.getClass().getAnnotation(CodePrefix.class);
            C c = object.getClass().getField(codeEnum.name()).getAnnotation(C.class);
            Code code = new Code();
            int cod = c != null ? c.value() : codeEnum.ordinal();

            code.setCode(codePrefix != null ? String.format("%s%0"+LEFT_PAD+"d", codePrefix.value(), cod)
                    : String.valueOf(cod));
            CODE_MAP.put(object,code);
            return code;
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
