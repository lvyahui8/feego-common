package io.github.lvyahui8.security;

import io.github.lvyahui8.core.security.CryptologySecurityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/13 22:34
 */
public class CryptologySecurityUtilsTest
{

    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    private KeyPair keyPair;

    @Before
    public void setUp() throws Exception {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance(CryptologySecurityUtils.getKeyAlgorithm(SIGNATURE_ALGORITHM));
        kpGen.initialize(1024);
        this.keyPair = kpGen.generateKeyPair();
    }

    @Test
    public void testSignature() throws Exception {
        String text = "hello, world";
        byte[] sign = CryptologySecurityUtils.sign(text.getBytes(), keyPair.getPrivate().getEncoded(), SIGNATURE_ALGORITHM);
        System.out.println(new BASE64Encoder().encode(sign));
        Assert.assertTrue(CryptologySecurityUtils.verify(text.getBytes(),sign,keyPair.getPublic().getEncoded(),SIGNATURE_ALGORITHM));
        Assert.assertFalse(CryptologySecurityUtils.verify(text.substring(1).getBytes(), sign, keyPair.getPublic().getEncoded(), SIGNATURE_ALGORITHM));
    }
}
