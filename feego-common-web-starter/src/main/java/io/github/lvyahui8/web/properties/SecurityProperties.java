package io.github.lvyahui8.web.properties;

import lombok.Data;

import java.util.List;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/14 0:39
 */
@Data
public class SecurityProperties {
    Signature signature;

    @Data
    public static class Signature {
        Boolean open = false;

        String signResponsePrivateKey;

        List<ClientApplication> clients;

        String algorithm = "SHA1withRSA";

        String defaultVerifyRequestPublicKey;

        @Data
        public static class ClientApplication {
            String appId;
            String verifyRequestPublicKey;
        }
    }
}
