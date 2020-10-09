package io.github.lvyahui8.web.context;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/10
 */
public class RequestMessage {
    String traceId;
    boolean stressTraffic;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public boolean isStressTraffic() {
        return stressTraffic;
    }

    public void setStressTraffic(boolean stressTraffic) {
        this.stressTraffic = stressTraffic;
    }
}
