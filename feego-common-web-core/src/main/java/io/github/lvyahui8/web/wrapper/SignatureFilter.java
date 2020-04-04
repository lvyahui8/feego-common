package io.github.lvyahui8.web.wrapper;

import com.google.common.base.Splitter;
import io.github.lvyahui8.web.constant.WebConstant;
import io.github.lvyahui8.web.signature.SignatureService;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/4 23:06
 */
public class SignatureFilter implements Filter {

    SignatureService signatureService;

    public SignatureFilter(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        ResettableHttpServletRequest wrappedRequest = new ResettableHttpServletRequest((HttpServletRequest) servletRequest);
        String signatureHeadValue = wrappedRequest.getHeader(WebConstant.HttpHeader.X_SIGNATURE.getKey());
        if (signatureHeadValue != null) {
            Map<String, String> signatureParams = Splitter.on(',').withKeyValueSeparator('=').split(signatureHeadValue);
            String encodedSignature = signatureParams.get(WebConstant.SignatureHeaderKey.SIGNATURE);
            String httpBody = wrappedRequest.getHttpBody();
            String queryString = wrappedRequest.getQueryString();
            boolean noData = StringUtils.isBlank(encodedSignature) && StringUtils.isBlank(queryString) && StringUtils.isBlank(httpBody);
            String text = null;
            boolean validSignature = false;
            if(! noData) {
                text =  (queryString != null ? queryString : "")  + '.' + (httpBody != null ? httpBody : "");
                try {
                    validSignature = signatureService.verifyRequest(text, encodedSignature);
                } catch (Exception ignored) {
                }
            }
            if (noData || validSignature) {
                filterChain.doFilter(wrappedRequest,servletResponse);
            }
        }
        throw new SecurityException("Illegal signature");
    }
}
