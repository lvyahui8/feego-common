package io.github.lvyahui8.sdk.reddot;

import java.util.Map;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/22
 */
public interface RedDotManager {
    /**
     * 激活key对应的红点
     *
     * @param key 红点维度
     * @param redDots 待激活红点列表
     */
    void enable(Object key, RedDot... redDots);

    /**
     * 带版本号激活红点，只有版本号比记录的红点大才能激活红点
     *
     * @param key 红点维度
     * @param redDot 红点
     * @param version 版本号
     */
    void enable(Object key, RedDot redDot,Long version);

    /**
     * 判断某个红点是否被激活
     *
     * @param key 红点维度
     * @param redDot 红点
     * @return 是否激活
     */
    boolean isActive(Object key, RedDot redDot);

    /**
     * 一次性查询多个红点是否激活
     *
     * @param key 红点维度
     * @param redDots 红点列表
     * @return 激活map
     */
    Map<RedDot,Boolean> isActiveMap(Object key, RedDot... redDots);

    /**
     * 消除红点
     *
     * @param key 红点维度
     * @param redDots 红点列表
     */
    void disable(Object key, RedDot ... redDots);
}
