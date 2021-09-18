package io.github.lvyahui8.sdk.weak;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/9/18
 */
@FunctionalInterface
public interface OnException<R> {
    R apply();
}
