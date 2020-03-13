package io.github.lvyahui8.core.security;

import org.apache.commons.lang3.StringUtils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/13 21:55
 */
@SuppressWarnings({"unused"})
public class CryptologySecurityUtils {
    public static byte [] sign(byte [] textBytes,byte[] encodedPrivateKey,String signatureAlgorithm) throws GeneralSecurityException {
        String keyAlgorithm = getKeyAlgorithm(signatureAlgorithm);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory factory = KeyFactory.getInstance(keyAlgorithm);
        PrivateKey privateKey = factory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initSign(privateKey);
        signature.update(textBytes);
        return signature.sign();
    }

    public static boolean verify(byte [] textBytes,byte [] signedBytes,byte [] encodedPublicKey,String signatureAlgorithm) throws GeneralSecurityException {
        String keyAlgorithm = getKeyAlgorithm(signatureAlgorithm);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory factory = KeyFactory.getInstance(keyAlgorithm);
        PublicKey publicKey = factory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signatureAlgorithm);
        signature.initVerify(publicKey);
        signature.update(textBytes);
        return signature.verify(signedBytes);
    }

    public static String getKeyAlgorithm(String signAlgorithm) {
        /**
         * MD2withRSA,SHA1withRSA,SHA256withRSA,SHA384withRSA,SHA512withRSA....
         * SHA1withDSA,SHA256withDSA,SHA512withDSA...
         */
        return StringUtils.substringAfter(signAlgorithm, "with");
    }
}
