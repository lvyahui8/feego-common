package io.github.lvyahui8.core.security;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/12 21:20
 */
public interface SignatureService {
    /**
     *  对响应加签
     *
     * @param text
     * @return
     */
    String signResponse(String text);

    /**
     * 对请求验签
     *
     * @param signedText
     * @return
     */
    String verifyRequest(String signedText);
}
