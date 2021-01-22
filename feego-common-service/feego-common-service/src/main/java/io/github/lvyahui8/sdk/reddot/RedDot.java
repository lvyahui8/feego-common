package io.github.lvyahui8.sdk.reddot;


/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/22
 */
public interface RedDot {
    String id();

    RedDot parent();

    boolean isLeaf();
}
