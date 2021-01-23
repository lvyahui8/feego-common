package io.github.lvyahui8.sdk.reddot;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/22
 */
public class DefaultRedDotManager implements RedDotManager {

    public StringRedisTemplate redisTemplate;

    public DefaultRedDotManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void enable(Object key, RedDot... redDots) {
        for (RedDot redDot : redDots) {
            enable(key, redDot,null);
        }
    }

    @Override
    public void enable(Object key, RedDot redDot, Long version) {
        if (! redDot.isLeaf()) {
            // 只有叶子红点可以被激活，非叶子红点只能被级联激活
            return ;
        }

        if (version != null) {
            RedDotInstance instance = redisTemplate.<String, RedDotInstance>opsForHash().get(key.toString(), redDot.id());
            if (instance != null) {
                if (instance.getV() != null && version <= instance.getV()) {
                    // 版本号低，不能激活
                    return;
                }
            }
        }
        
        List<String> redDotLink = getRedDotLink(redDot);
        List<RedDotInstance> redDotInstances = redisTemplate.<String, RedDotInstance>opsForHash().multiGet(key.toString(), redDotLink);
        Map<String,RedDotInstance> instanceMap = redDotInstances.stream().collect(Collectors.toMap(RedDotInstance::getRid,a -> a));

        for (int i = 0; i < redDotLink.size(); i++) {
            String redDotId = redDotLink.get(i);
            boolean containsKey = instanceMap.containsKey(redDotId);
            RedDotInstance instance;
            if (containsKey) {
                instance = instanceMap.get(redDotId);
            } else {
                instance = new RedDotInstance();
                instance.setCause(new LinkedList<>());
            }
            instance.setRid(redDotId);
            instance.setActive(true);
            instance.setActivatedTs(System.currentTimeMillis());
            if (i == 0) {
                instance.setV(version);
            } else {
                instance.getCause().add(redDotLink.get(0));
            }
            if (! containsKey) {
                instanceMap.put(redDotId,instance);
            }
        }

        redisTemplate.<String,RedDotInstance>opsForHash().putAll(key.toString(),instanceMap);
    }

    private List<String> getRedDotLink(RedDot redDot) {
        List<String> ret = new LinkedList<>();
        do{
            ret.add(redDot.id());
        } while((redDot = redDot.parent()) != null);
        return ret;
    }

    @Override
    public boolean isActive(Object key, RedDot redDot) {
        return isActiveMap(key,redDot).get(redDot.id());
    }

    @Override
    public Map<String, Boolean> isActiveMap(Object key, RedDot... redDots) {
        Map<String, Boolean> activeMap = Arrays.stream(redDots).collect(Collectors.toMap(RedDot::id, a -> false));
        List<RedDotInstance> redDotInstances = redisTemplate.<String, RedDotInstance>opsForHash().multiGet(key.toString(), activeMap.keySet());
        for (RedDotInstance instance : redDotInstances) {
            if (Boolean.TRUE.equals(instance.getActive())) {
                activeMap.put(instance.getRid(),true);
            }
        }
        return activeMap;
    }

    @Override
    public void disable(Object key, RedDot... redDots) {

    }

}
