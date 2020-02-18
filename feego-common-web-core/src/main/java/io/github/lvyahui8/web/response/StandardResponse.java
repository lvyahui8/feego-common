package io.github.lvyahui8.web.response;

import lombok.Data;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/18 21:58
 */
@Data
public class StandardResponse  {
    private String code;
    private String msg;
    private Object data;
    private int cost;

    public static StandardResponse success(Object data) {
        StandardResponse response = new StandardResponse();
        response.setCode("0");
        response.setMsg("success");
        response.setData(data);
        return response;
    }
}
