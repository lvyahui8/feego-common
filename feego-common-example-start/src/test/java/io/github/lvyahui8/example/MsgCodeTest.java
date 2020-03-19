package io.github.lvyahui8.example;

import io.github.lvyahui8.example.code.Code;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/18 21:56
 */
public class MsgCodeTest {

    enum SimpleMsgCode {
        success(0,"success"),
        unknown(-1 , "unknown exception"),
        ;
        String msg;
        Integer code;

        SimpleMsgCode(Integer code,String msg) {
            this.msg = msg;
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    @Test
    public void testBasic() throws Exception {
        System.out.println(Code.General.success.getCode());
        System.out.println(Code.General.success.getMsg());
        System.out.println(Code.General.unknown.getCode());
        System.out.println(Code.General.unknown.getMsg());
        System.out.println(Code.Client.no_permit.getCode());
        System.out.println(Code.Client.no_permit.getMsg());
        System.out.println(Code.Client.no_login_session.getCode());
        System.out.println(Code.Client.no_login_session.getMsg());
    }

    @Test
    public void testPerformance() throws Exception {
        /// final int n = new DecimalFormat(",####").parse("1,0000,0000").intValue();
        final int n = new DecimalFormat(",####").parse("100,0000").intValue();
        System.out.println("loop times:" + n);
        long begin = System.currentTimeMillis();
        /// // 预先加载到缓存, 实际测试并没有太大区别
        /// Code.General.success.getCode();
        for (int i = 0 ;i < n ; i ++) {
            SimpleMsgCode.success.getCode();
        }
        System.out.println("simpleMsgCode.getCode cost time:" + (System.currentTimeMillis() - begin) + " ms");
        begin = System.currentTimeMillis();
        for (int i = 0 ; i < n ; i ++) {
            Code.General.success.getCode();
        }
        System.out.println("MsgCode.getCode cost time:" + (System.currentTimeMillis() - begin) + " ms");
        begin = System.currentTimeMillis();
        for (int i = 0 ;i < n ; i ++) {
            SimpleMsgCode.success.getMsg();
        }
        System.out.println("simpleMsgCode.getMsg cost time:" + (System.currentTimeMillis() - begin) + " ms");
        begin = System.currentTimeMillis();
        for (int i = 0 ; i < n ; i ++) {
            Code.General.success.getMsg();
        }
        System.out.println("MsgCode.getMsg cost time:" + (System.currentTimeMillis() - begin) + " ms");
    }
}
