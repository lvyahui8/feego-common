package io.github.lvyahui8.sdk.cache;

import com.google.gson.Gson;
import io.github.lvyahui8.sdk.utils.AsyncTaskExecutor;
import io.github.lvyahui8.sdk.utils.RetryUtils;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/20
 */
public abstract class AsyncRefreshableCacheObject<QUERY_PARAM, VAL_TYPE> {

    private static final Gson gson = new Gson();

    private final StringRedisTemplate redisTemplate;

    private static class CacheValueTemplate<VAL> {
        VAL v;
        long expiredTs;

        public VAL getV() {
            return v;
        }

        public void setV(VAL v) {
            this.v = v;
        }

        public long getExpiredTs() {
            return expiredTs;
        }

        public void setExpiredTs(long expiredTs) {
            this.expiredTs = expiredTs;
        }
    }

    protected class CacheValue extends CacheValueTemplate<VAL_TYPE> {

    }

    public AsyncRefreshableCacheObject(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public VAL_TYPE get(final QUERY_PARAM queryParam) {
        String data ;
        try {
            data = RetryUtils.retryGet(() -> redisTemplate.opsForValue().get(getKey(queryParam)),3);
        } catch (Exception e) {
            throw new RedisSystemException("Failed to retry query data",e);
        }
        CacheValue cacheValue = null;
        try{
            cacheValue = gson.fromJson(data, CacheValue.class);
        } catch (Exception ignored) { }

        if (cacheValue == null) {
            cacheValue = loadObject(queryParam);
        } else if ((cacheValue.getExpiredTs() + getLogicTimeout()) >= System.currentTimeMillis()){
            AsyncTaskExecutor.submit(() -> loadObject(queryParam));
        }

        return cacheValue.getV();
    }

    public VAL_TYPE load(final QUERY_PARAM queryParam) {
        CacheValue cacheValue = loadObject(queryParam);
        return cacheValue.getV();
    }

    private CacheValue loadObject(final QUERY_PARAM queryParam) {
        CacheValue cacheValue = new CacheValue();
        cacheValue.setV(syncLoad(queryParam));
        cacheValue.setExpiredTs(getLogicTimeout());
        redisTemplate.opsForValue().set(getKey(queryParam),gson.toJson(cacheValue));
        return cacheValue;
    }

    protected abstract VAL_TYPE syncLoad(QUERY_PARAM queryParam);
    protected abstract String getKey(QUERY_PARAM queryParam);
    protected abstract long getLogicTimeout();
}

