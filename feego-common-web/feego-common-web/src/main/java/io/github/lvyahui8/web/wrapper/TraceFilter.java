package io.github.lvyahui8.web.wrapper;

import io.github.lvyahui8.sdk.guid.GUIDGenerator;
import io.github.lvyahui8.web.context.RequestContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2020/10/1
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            RequestContext.setTraceId(GUIDGenerator.createGUID());
            chain.doFilter(request, response);
        } finally {
            RequestContext.clean();
        }
    }

}
