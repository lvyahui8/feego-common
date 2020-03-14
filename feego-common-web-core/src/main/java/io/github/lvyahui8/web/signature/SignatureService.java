package io.github.lvyahui8.web.signature;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/12 21:20
 */
public interface SignatureService {
    /**
     *  对响应加签
     *
     * @param text 待签名内容
     * @return encodedSignature
     */
    String signResponse(String text);

    /**
     * 对请求验签
     *
     * @param text 待签名内容
     * @param encodedSignature 已编码签名
     * @return 签名是否匹配
     */
    boolean verifyRequest(String text,String encodedSignature);
}
