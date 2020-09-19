package io.github.lvyahui8.web.request;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/14 0:30
 */
@Data
public class SignedRequest {
    Object body;
    String signature;
}
