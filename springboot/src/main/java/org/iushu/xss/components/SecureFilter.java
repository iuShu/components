package org.iushu.xss.components;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.iushu.xss.SecureConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @see SecureConfiguration#secureFilter()
 * @see SecureConfiguration#filterRegistrationBean()
 *
 * @author iuShu
 * @since 6/8/21
 */
public class SecureFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("[SecureFilter] nothing to do");

        if (!ServletFileUpload.isMultipartContent(request))
            request = new SecureHttpRequestWrapper(request);    // switch request
        filterChain.doFilter(request, response);                // proceed
    }

}
