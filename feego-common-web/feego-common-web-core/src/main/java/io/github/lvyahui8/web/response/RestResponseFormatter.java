package io.github.lvyahui8.web.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 23:06
 */
@RestControllerAdvice
@Slf4j
public class RestResponseFormatter implements ResponseBodyAdvice {

    @Autowired
    ObjectMapper objectMapper ;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof String) {
            try {
                ((ServletServerHttpResponse) serverHttpResponse).getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                return objectMapper.writeValueAsString(StandardResponse.success(o));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Serialized response failed",e);
            }
        }
        if (o instanceof StandardResponse) {
            return o;
        }
        return StandardResponse.success(o);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody Object handle(HttpServletRequest request,Exception e) {
        log.error("request uri:" +request.getRequestURL() ,e);
        return StandardResponse.success(null);
    }
}
