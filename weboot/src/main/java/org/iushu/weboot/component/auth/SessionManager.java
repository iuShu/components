package org.iushu.weboot.component.auth;

import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.CacheKeys;
import org.iushu.weboot.event.UserLoginEvent;
import org.iushu.weboot.service.StaffService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.Duration;

import static org.iushu.weboot.component.CacheKeys.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * session      user token userId
 * cookie       token userId
 * redis        token staff
 *
 * @author iuShu
 * @since 6/24/21
 */
@Component
public class SessionManager implements ApplicationContextAware {

    public static final User PASSWORD_WRONG = new User();
    public static final User RE_LOGIN = new User();

    private static final String KEY_USER = "wbu";
    private static final String KEY_USER_ID = "wbuid";
    private static final String KEY_TOKEN = "wbtkn";

    private static final int SESSION_EXPIRE_MILLIS = 2 * 60 * 60 * 1000;  // 2 hours
    private static final int COOKIE_EXPIRE_SEC = 24 * 60 * 60;  // 1 day

    private ApplicationContext applicationContext;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(KEY_USER);
    }

    public User getLoggedUser(HttpServletRequest request, HttpServletResponse response) {

        // TODO to be finished

        return null;
    }

    public User login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        User user = staffService.getUser(username);
        if (user == null)
            return null;

        if (!password.equals(user.getPassword()))
            return PASSWORD_WRONG;

        // TODO to be finished

        applicationContext.publishEvent(new UserLoginEvent(user));
        return user;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        short userId = (short) request.getSession().getAttribute(KEY_USER_ID);
        String token = (String) request.getSession().getAttribute(KEY_TOKEN);
        manageCookie(response, token, userId, 0);   // delete cookies
        redisTemplate.delete(CacheKeys.userKey(userId));    // delete cache
        request.getSession().removeAttribute(KEY_TOKEN);
        request.getSession().removeAttribute(KEY_USER);
        request.getSession().removeAttribute(KEY_USER_ID);
    }

    private void manageCookie(HttpServletResponse response, String token, short userId, int maxAge) {
        Cookie tokenCookie = new Cookie(KEY_TOKEN, token);
        tokenCookie.setDomain("iushu.com");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(maxAge);
        tokenCookie.setHttpOnly(true);

        Cookie userCookie = new Cookie(KEY_USER_ID, String.valueOf(userId));
        userCookie.setDomain("iushu.com");
        userCookie.setPath("/");
        userCookie.setMaxAge(maxAge);
        userCookie.setHttpOnly(true);

        response.addCookie(tokenCookie);
        response.addCookie(userCookie);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
