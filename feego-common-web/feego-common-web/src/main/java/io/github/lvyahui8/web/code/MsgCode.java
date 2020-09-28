package io.github.lvyahui8.web.code;


/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:38
 */
public interface MsgCode {
    /**
     * 获取消息
     *
     * @return msg
     */
    default String getMsg() {
        return CodeRepository.get(this).getMsg();
    }

    /**
     * 获取code
     *
     * @return code
     */
    default String getCode() {
        return CodeRepository.get(this).getCode();
    }
}
