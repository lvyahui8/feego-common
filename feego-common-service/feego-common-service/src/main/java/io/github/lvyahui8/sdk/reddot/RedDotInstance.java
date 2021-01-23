package io.github.lvyahui8.sdk.reddot;

import java.util.List;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/23
 */
public class RedDotInstance {
    Boolean active;
    /**
     * dimension key
     */
    String dimKey;
    /**
     * redDot id
     */
    String rid;
    /**
     * 实例版本号
     */
    Long v;
    /**
     * 激活的时间戳
     */
    Long activatedTs;
    /**
     * 引起此红点激活的原因
     */
    List<String> cause;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDimKey() {
        return dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Long getActivatedTs() {
        return activatedTs;
    }

    public void setActivatedTs(Long activatedTs) {
        this.activatedTs = activatedTs;
    }

    public List<String> getCause() {
        return cause;
    }

    public void setCause(List<String> cause) {
        this.cause = cause;
    }
}
