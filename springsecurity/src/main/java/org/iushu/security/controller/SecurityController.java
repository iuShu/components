package org.iushu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author iuShu
 * @since 8/9/21
 */
@Controller
public class SecurityController {

    @RequestMapping("/to_login")
    public String toLogin() {
        return "/login.html";
    }

}
