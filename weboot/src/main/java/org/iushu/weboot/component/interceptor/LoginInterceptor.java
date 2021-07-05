package org.iushu.weboot.component.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iushu.weboot.annotation.AccessLimit;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.auth.SessionManager;
import org.iushu.weboot.exchange.Response;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author iuShu
 * @since 6/24/21
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private SessionManager sessionManager;

    public LoginInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;    // proceed

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object bean = handlerMethod.getBean();
        if (bean.getClass().getName().startsWith("org.springframework"))
            return true;    // built-in components

        AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
        if (accessLimit != null && !accessLimit.login())
            return true;

        User user = sessionManager.getLoggedUser(request, response);
        if (user == null) {
            render(response, Response.failure("please login first"));
            return false;
        }

        return true;    // proceed
    }

    private void render(HttpServletResponse response, Response webootResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        byte[] bytes = new ObjectMapper().writeValueAsBytes(webootResponse);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

}
