package org.iushu.web.component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author iuShu
 * @since 4/12/21
 */
public class PumpkinFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[init] " + filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("[execute] " + servletRequest.getClass().getName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("[destroy] " + this.getClass().getName());
    }
}
