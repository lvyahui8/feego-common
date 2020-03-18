package io.github.lvyahui8.web.code;


/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:38
 */
public interface MsgCode {
    /**
     * 获取消息
     *
     * @return
     */
    default String getMsg() {
        C c = CodeRepository.get(this);
        return c.msg().length() > 0 ? c.msg() : ((Enum<?>)this).name();
    }

    /**
     * 获取code
     *
     * @return
     */
    default Integer getCode() {
        return CodeRepository.get(this).code();
    }
}
