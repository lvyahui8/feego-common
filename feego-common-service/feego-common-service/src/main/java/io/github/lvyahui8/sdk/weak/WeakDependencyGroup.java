package io.github.lvyahui8.sdk.weak;

import io.github.lvyahui8.sdk.utils.AsyncTaskExecutor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/9/18
 */
public class WeakDependencyGroup {

    public List<CompletableFuture<?>> futures = new LinkedList<>();

    public static class WeakDependencyItem<RES> {
        CompletableFuture<RES> future;
        OnException<RES> onException;
    }

    public List<WeakDependencyItem<?>> weakDependencyItems;

    public <RES> WeakDependencyGroup add(Supplier<RES> weakDependency, OnException<RES> onException) {
        WeakDependencyItem<RES> weakDependencyItem = new WeakDependencyItem<>();
        try {
            weakDependencyItem.future = CompletableFuture.supplyAsync(() -> {
                try {
                    return weakDependency.get();
                } catch (Exception e) {
                    return onException.apply(e);
                }
            }, AsyncTaskExecutor.getExecutor());
            futures.add(weakDependencyItem.future);
        } catch (Exception ignored) {
        }
        weakDependencyItem.onException = onException;
        return this;
    }

    public Map<Type,Object> get(long timeoutMs) {
        if (timeoutMs > 0){
            CompletableFuture<Void> future = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            try {
                future.get(timeoutMs, TimeUnit.MICROSECONDS);
            } catch (Exception ignored) {
            }
        }
        Map<Type,Object> res = new HashMap<>();
        for (WeakDependencyItem<?> weakDependencyItem : weakDependencyItems) {
            Type retType = ((ParameterizedType) weakDependencyItem.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Object ret = null;
            if (weakDependencyItem.future == null) {
                ret = weakDependencyItem.onException.apply(new TimeoutException());
            } else {
                try {
                    ret = weakDependencyItem.future.getNow(null);
                } catch (Exception e) {
                    ret = weakDependencyItem.onException.apply(e);
                }
            }
            res.put(retType,ret);
        }
        return res;
    }
}

