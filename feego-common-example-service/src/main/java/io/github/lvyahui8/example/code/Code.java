package io.github.lvyahui8.example.code;

import io.github.lvyahui8.web.code.C;
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
        @C(code = 0) success,
        @C(code = -1,msg = "unknown exception") unknown,
    }
}
