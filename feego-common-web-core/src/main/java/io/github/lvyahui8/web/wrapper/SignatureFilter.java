package io.github.lvyahui8.web.wrapper;

import com.google.common.base.Splitter;
import io.github.lvyahui8.web.constant.WebConstant;
import io.github.lvyahui8.web.signature.SignatureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/4 23:06
 */
@Slf4j
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
        String httpBody = wrappedRequest.getHttpBody();
        String queryString = wrappedRequest.getQueryString();
        boolean noData = StringUtils.isBlank(queryString) && StringUtils.isBlank(httpBody);
        if (noData && StringUtils.isBlank(signatureHeadValue)) {
            filterChain.doFilter(wrappedRequest,servletResponse);
        } else if (StringUtils.isNotBlank(signatureHeadValue)) {
            Iterator<String> trimResults = Splitter.on(',').omitEmptyStrings().trimResults().split(signatureHeadValue).iterator();
            Map<String, String> signatureParams = new LinkedHashMap<>();
            while(trimResults.hasNext()) {
                String item = trimResults.next().trim();
                if (item.length() < 2) {
                    continue;
                }
                int index = item.indexOf('=');
                signatureParams.put(item.substring(0,index).trim(),item.substring(index + 1).trim());
            }
            String encodedSignature = signatureParams.get(WebConstant.SignatureHeaderKey.SIGNATURE);
            String text = null;
            boolean validSignature = false;
            if(! noData) {
                text =  (queryString != null ? queryString : "")  + '.' + (httpBody != null ? httpBody : "");
                try {
                    validSignature = signatureService.verifyRequest(text, encodedSignature);
                } catch (Exception ignored) {
                    log.error("verifyRequest failed",ignored);
                }
            }
            if (validSignature) {
                filterChain.doFilter(wrappedRequest,servletResponse);
            } else {
                throw new SecurityException("Illegal signature");
            }
        } else {
            throw new SecurityException("Illegal signature");
        }
    }
}
