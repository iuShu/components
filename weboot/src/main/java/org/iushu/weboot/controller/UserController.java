package org.iushu.weboot.controller;

import org.iushu.weboot.bean.User;
import org.iushu.weboot.exchange.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iuShu
 * @since 7/1/21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/info")
    public Response<User> user(User user) {
        return Response.payload(user);
    }

}
