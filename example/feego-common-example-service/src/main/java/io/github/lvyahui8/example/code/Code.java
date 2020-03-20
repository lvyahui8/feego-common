package io.github.lvyahui8.example.code;

import io.github.lvyahui8.web.code.C;
import io.github.lvyahui8.web.code.CodePrefix;
import io.github.lvyahui8.web.code.MsgCode;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:42
 */
public interface Code {
    enum General implements MsgCode {
        /**
         * 成功
         */
        @C(0) success,
        @C(value = -1,msg = "unknown exception") unknown,
        ;
    }

    @CodePrefix("4")
    enum Client implements MsgCode {
        /**
         * 客户端相关错误
         */
        no_permit,
        @C(301) no_login_session,
        ;
    }

    @CodePrefix("5")
    enum Component implements MsgCode {

    }
}
