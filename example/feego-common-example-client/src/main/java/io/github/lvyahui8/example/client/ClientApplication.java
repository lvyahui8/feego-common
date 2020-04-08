package io.github.lvyahui8.example.client;

import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;
import io.github.lvyahui8.core.security.CryptologySecurityUtils;
import io.github.lvyahui8.example.client.service.OrderService;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:15
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "io.github.lvyahui8.example.client")
@PropertySource("classpath:dubbo-consumer.properties")
@ModuleLoggerAutoGeneration({"order"})
@Slf4j
public class ClientApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.createOrder(1L);

        String httpBody = "{\n" +
                "  \"username\" : \"feego\"\n" +
                "}";
        String key  =
                "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANMQDJAjncKfZ+Hw\n" +
                "lNB2sW01AiSzuD77bnf3YF3Iy/J3+8a5mmm1u4fEDwlc9Vkm6doY1ExWoVt8e/eG\n" +
                "xH7UgViGm2MC0RVcmwSQEf/YGl2iv+YXFgWhN/4LLrSbekFV2m9zPUAdifRMMo0v\n" +
                "ffDlG4xtdTngVhliA4i4zs/+I2ZBAgMBAAECgYEAuR5FdpLWhuIbUi8ZxvGj0t4A\n" +
                "YEKFIjCTBoOhMQCx6JvV58nYpkjwDzcl3Rd8VdJsn47RfZcp9Tzs2gCJDZjDTFJj\n" +
                "wWsIWIRE77YX1K3TezBDdFh1rc2zUIy0u3jvbkPJguZeKFYtff2ACrVTnGybfpbS\n" +
                "s04e4WnjPJmgivmnazECQQD2moEgZjlaILQuURE59baY5TYqltZRRFvt+PrerJ5H\n" +
                "zWUNbD//bcsTSbDEw1+s7g654InPZvvjkbHL3AWBsj79AkEA2xrcgDzkEWrxzdmg\n" +
                "6qEeU3l/PcxKI66S7DEY74fdf8V1ZIQEHG1iyAAqfyADdXyofHqhABE4Bnub0mUn\n" +
                "SAjBlQJAbnMrDIDchSKKsQf8KDKGUxquVQrz+LgeWIqgiiVUSyKSdR2b2GKrhvsF\n" +
                "YkuDSafiDpyj/LHKddWmhYEJMlrMXQJBAMJ+1b4AT3Qmuv9AcNfWrlTrRUhWUHnc\n" +
                "Pg/shXYb5UPGxK61gfC6KTXg79hpUqi4P9hAAytMHa+jim753CTVoQECQQDD3myY\n" +
                "EOdz7JGlfcXaavtPRQlfo5FRQI8iR82rpUZ6H0ZoXpw+n1F+4X720g2ah76eMB7B\n" +
                "JbeETpR4PBckfjo1";
        Base64 base64 = new Base64();
        byte[] decodeKey = base64.decode(key);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/save");
        String headerKey = "X-Feego-Signature";
        httpPost.setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString());
        httpPost.setHeader(headerKey, "signature=" + base64.encodeAsString(CryptologySecurityUtils.sign(('.' + httpBody).getBytes(),decodeKey,"SHA1withRSA")));
        httpPost.setEntity(new StringEntity(httpBody));
        for (int i = 0; i < 100; i++) {
            HttpResponse response = httpClient.execute(httpPost);
            log.info("status:{}",response.getStatusLine());
            log.info("sign header:{}",response.getFirstHeader(headerKey).getValue());
            log.info(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8));
        }
    }
}
