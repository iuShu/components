package org.iushu.weboot.component;

import org.iushu.weboot.bean.User;
import org.iushu.weboot.event.UserLoginEvent;
import org.iushu.weboot.service.StaffService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Component
public class SessionManager implements ApplicationContextAware {

    public static final User PASSWORD_WRONG = new User();
    private static final String KEY_USER = "wbu";

    private ApplicationContext applicationContext;

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

    public User login(HttpServletRequest request, String username, String password) {
        User user = staffService.getUser(username);
        if (user == null)
            return null;
        else if (!password.equals(user.getPassword()))
            return PASSWORD_WRONG;

        // token manager

        HttpSession session = request.getSession();
        session.setAttribute(KEY_USER, user);

        // sync to redis

        applicationContext.publishEvent(new UserLoginEvent(user));

        return user;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
