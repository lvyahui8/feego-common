package io.github.lvyahui8.web.signature.impl;

import io.github.lvyahui8.core.security.CryptologySecurityUtils;
import io.github.lvyahui8.web.signature.SignatureService;
import io.github.lvyahui8.web.signature.SignatureSettings;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.commons.codec.binary.Base64;

import java.security.GeneralSecurityException;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/15 1:23
 */
public class SignatureServiceImpl implements SignatureService {

    private SignatureSettings signatureSettings;

    private byte [] verifyRequestKey ;

    Base64 base64 ;

    public SignatureServiceImpl(SignatureSettings signatureSettings) {
        this.signatureSettings = signatureSettings;
        this.base64 = new Base64();
        verifyRequestKey = base64.decode(signatureSettings.getDefaultVerifyRequestPublicKey());
    }

    @Override
    public String signResponse(String text) {
        return null;
    }

    @Override
    public boolean verifyRequest(String text, String encodedSignature) {
        try {
            return CryptologySecurityUtils.verify(text.getBytes(),base64.decode(encodedSignature),verifyRequestKey,signatureSettings.getAlgorithm());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

}
