package io.github.lvyahui8.example.configuration;


import io.github.lvyahui8.sdk.logging.context.LogContextHolder;
import io.github.lvyahui8.sdk.logging.logger.LogEventReactor;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/15
 */
public class HttpRequestLogContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            LogContextHolder.initDiscardedEventQueue(null);
            chain.doFilter(request,response);
        } finally {
            LogEventReactor.replayDiscardedEventsIfHasError();
            LogContextHolder.resetLogContext();
        }
    }
}
