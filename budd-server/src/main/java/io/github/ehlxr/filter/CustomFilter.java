package io.github.ehlxr.filter;

import io.github.ehlxr.common.Constant;
import io.github.ehlxr.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * auth filter
 *
 * @author supeng
 * @date 2020/08/31
 */
@Order(value = 1)
@WebFilter(filterName = "customFilter", urlPatterns = {"/api/*"})
public class CustomFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("customFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        MDC.put(Constant.LOG_TRACE_ID, String.valueOf(IdUtil.localId()));
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove(Constant.LOG_TRACE_ID);
    }

    @Override
    public void destroy() {
        log.info("customFilter destroy");
    }
}
