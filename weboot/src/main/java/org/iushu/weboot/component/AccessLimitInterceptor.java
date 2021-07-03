package org.iushu.weboot.component;

import org.iushu.weboot.annotation.AccessLimit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * restrict server request frequency
 *
 * TODO to be finished
 *
 * @author iuShu
 * @since 6/24/21
 */
//@Component
//@PropertySource("classpath:application.properties")
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Value("weboot.access.limit.milliseconds")
    private int milliseconds;

    @Value("weboot.access.limit.frequency")
    private int frequency;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;    // proceed

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        return true;
    }
}
