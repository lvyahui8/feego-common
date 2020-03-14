# 加签验签

## 签名的意义

我们已经有了https， 为什么还要做签名？ 简单的把两者做下对比

| 项目               | 签名                                 | HTTPS（HTTP+SSL/TLS） |
| ------------------ | ------------------------------------ | --------------------- |
| 网络层级           | 应用层                               | 传输层                |
| 主要目的和用途     | 防篡改，证明原文的合法性             | 防窃取、防窥视、保密  |
| 对待原始内容的态度 | 可以明文                             | 绝不能泄漏明文        |
| 秘钥对的用法       | 私钥加签、公钥验签                   | 公钥加密、私钥解密    |
| 保障对象           | 发送方与接收方，不管中间经过多少节点 | 两个通信节点之间      |

## 签名方案

### 签名对象

鉴于许多的中间件、代理类服务， 会修改或附加HTTP Header，或者修改Request PATH。 因此， Feego Common框架只对关键的数据内容加签， 业务方不能依赖HTTP头部无篡改。

对请求， 要求客户端按照约定对如下字段加签

- 客户端标识（keyId/clientId）
- 请求发起时间戳
- queryString
- httpReqeustBody

即

```java
signature = base64encode(SHA1-RSA(客户端标识.请求发起时间戳.queryString.httpReqeustBody))
```

客户端可以使用SDK发送加签请求

对于响应，服务端对以下字段加签

- 响应的时间戳
- httpResponseBody

即

```
signature = base64encode(SHA1-RSA(响应的时间戳.httpResponseBody))
```

### 传输签名结果

签名结果放在http header中传输。

header name为`X-Feego-Signature`

header value为key-value格式的键值列表，以`,`分隔，其中键有以下几个

- clientId ：表示客户端标识，可以为空
- algorithm ：表示签名算法，可以为空
- signature ：表示签名结果，必选。

一个完整的签名header示例：

### 秘钥对的维护

一次请求。 完整的签名方案应该涉及到两次签名

- 客户端发送给服务端请求的签名处理
- 服务端发送给客户端响应的签名处理

因此， 完整的一次请求， 应该涉及到两个秘钥对， 一个是处理请求的秘钥对， 一个是处理响应的秘钥对。

针对响应，由服务端使用私钥加签， 因为服务端只有一个， 只需要一个秘钥对即可自证身份。因此，所有客户端可以共用同一个公钥对响应验签。

对于请求而言， 客户端可能有非常多个， 每一个客户端的身份是不同的， 服务端应该跟每一个客户端之间， 维护一个秘钥对。

目前Feego commn使用配置维护秘钥对，配置格式如下

```java
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
```

