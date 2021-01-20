package io.github.lvyahui8.example.configuration;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/20
 */
public enum CacheKey {
    user_profile("up"),
    ;

    String key;

    CacheKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
