package org.iushu.weboot.controller;

import org.iushu.weboot.annotation.AccessLimit;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.SessionManager;
import org.iushu.weboot.exchange.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iuShu
 * @since 6/24/21
 */
@RestController
public class WebootController {

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/login")
    @AccessLimit(login = false)
    public Response login(String username, String password) {

        // parameter validation

        User user = sessionManager.login(username, password);

        // redirect to refer

        return Response.success(user);
    }

}
