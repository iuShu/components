package org.iushu.weboot.controller;

import org.iushu.weboot.annotation.AccessLimit;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.SessionManager;
import org.iushu.weboot.exchange.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author iuShu
 * @since 6/24/21
 */
@RestController
public class WebootController {

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    @AccessLimit(login = false)
    public Response login(HttpServletRequest request, HttpServletResponse response, String username, String password) throws IOException {
        User user = sessionManager.login(request, username, password);
        if (user == null)
            return Response.failure("No such user, please register first");
        else if (user == SessionManager.PASSWORD_WRONG)
            return Response.failure("wrong password");

        String referer = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.isEmpty(referer))
            return Response.success(user);

        response.sendRedirect(referer);
        return Response.success();
    }

    @RequestMapping("/logout")
    public Response logout(HttpServletRequest request) {
        return Response.success();
    }

}
