package io.github.lvyahui8.web.constant;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/4 23:36
 */
public interface WebConstant {
    public enum HttpHeader {
        /**
         * 常用头
         */
        X_SIGNATURE("X-Feego-Signature"),
        ;

        private String key;

        HttpHeader(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public interface SignatureHeaderKey  {
        String CLIENT_ID = "clientId";
        String ALGORITHM = "algorithm";
        String SIGNATURE = "signature";
    }
}
