package org.iushu.weboot.component;

import org.iushu.weboot.bean.User;
import org.iushu.weboot.service.StaffService;
import org.iushu.weboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Component
public class SessionManager {

    private static final String KEY_USER = "wbu";

    @Autowired
    private StaffService staffService;

    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(KEY_USER);
        if (user == null) {
            // search at redis for auto-login
        }
        return user;
    }

    public User login(String username, String password) {
        User user = staffService.getUser(username, password);

        // sync to redis

        // publish an user login event

        return user;
    }

}
