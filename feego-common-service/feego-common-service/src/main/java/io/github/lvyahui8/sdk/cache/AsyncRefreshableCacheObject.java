package io.github.lvyahui8.sdk.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.lvyahui8.sdk.utils.AsyncTaskExecutor;
import io.github.lvyahui8.sdk.utils.RetryUtils;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/20
 */
@SuppressWarnings({"unused","WeakerAccess"})
public abstract class AsyncRefreshableCacheObject<QUERY_PARAM, VAL_TYPE> {

    private static final long DEFAULT_KEY_MAX_TIMEOUT_MS = 30 * 24 * 60 * 60 ;

    private static final Gson gson = new Gson();

    private final StringRedisTemplate redisTemplate;

    private final Type cacheValueTypeToken;

    public AsyncRefreshableCacheObject(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        Type valType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.cacheValueTypeToken = TypeToken.getParameterized(CacheValue.class, valType).getType();
    }

    public VAL_TYPE get(final QUERY_PARAM queryParam) {
        String data ;
        try {
            data = RetryUtils.retryGet(() -> redisTemplate.opsForValue().get(getRedisKey(queryParam)),3);
        } catch (Exception e) {
            throw new RedisSystemException("Failed to retry query data",e);
        }
        CacheValue<VAL_TYPE> cacheValue = null;
        try{
            cacheValue = gson.fromJson(data, cacheValueTypeToken);
        } catch (Exception ignored) { }

        if (cacheValue == null) {
            // 首次初始化必须同步加载
            return load(queryParam);
        } else if (System.currentTimeMillis() > cacheValue.getExpiredTs()){
            AsyncTaskExecutor.execute(() -> load(queryParam));
        }

        return cacheValue.getV();
    }

    public VAL_TYPE load(final QUERY_PARAM queryParam) {
        return refresh(queryParam,syncLoad(queryParam));
    }

    public VAL_TYPE refresh(final QUERY_PARAM queryParam,final VAL_TYPE value) {
        CacheValue<VAL_TYPE> cacheValue = new CacheValue<>();
        cacheValue.setV(value);
        cacheValue.setExpiredTs(System.currentTimeMillis() + getLogicTimeout());
        RetryUtils.retryDo(
                () -> redisTemplate.opsForValue().set(getRedisKey(queryParam),gson.toJson(cacheValue),getMaxTimeout(),
                        TimeUnit.MILLISECONDS),
                3);
        return value;
    }

    protected long getMaxTimeout() {
        return DEFAULT_KEY_MAX_TIMEOUT_MS;
    }

    protected abstract VAL_TYPE syncLoad(QUERY_PARAM queryParam);
    protected abstract String getRedisKey(QUERY_PARAM queryParam);
    protected abstract long getLogicTimeout();

    protected static class CacheValue<VAL>  {
        /*
         * 泛型擦除后，所有的泛型参数实际都是Object.class。gson、fastjson使用CacheValue.class反序列化，v会写入hashMap之类的对象
         */
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
}

