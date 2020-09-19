package io.github.lvyahui8.web.signature;

import lombok.Data;

import java.util.List;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/15 1:26
 */
@Data
public class SignatureSettings {
    Boolean open = false;

    List<ClientApplication> clients;

    String algorithm = "SHA1withRSA";

    String defaultSignResponsePrivateKey;

    String defaultVerifyRequestPublicKey;

    @Data
    public static class ClientApplication {
        String appId;
        String verifyRequestPublicKey;
        String signResponsePrivateKey;
    }
}
