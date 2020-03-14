package io.github.lvyahui8.web.signature.impl;

import io.github.lvyahui8.web.signature.SignatureService;
import io.github.lvyahui8.web.signature.SignatureSettings;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/15 1:23
 */
public class SignatureServiceImpl implements SignatureService {

    private SignatureSettings signatureSettings;

    public SignatureServiceImpl(SignatureSettings signatureSettings) {
        this.signatureSettings = signatureSettings;
    }

    @Override
    public String signResponse(String text) {
        return null;
    }

    @Override
    public boolean verifyRequest(String text, String encodedSignature) {
        return false;
    }

}
