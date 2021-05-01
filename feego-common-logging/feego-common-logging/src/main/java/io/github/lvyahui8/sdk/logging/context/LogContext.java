package io.github.lvyahui8.sdk.logging.context;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/4/30
 */
public class LogContext {
    String biz;

    /**
     * transaction id/request id/trace id
     */
    String tid;

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
