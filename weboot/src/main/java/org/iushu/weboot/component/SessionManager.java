package org.iushu.weboot.component;

import org.apache.tomcat.util.security.MD5Encoder;
import org.iushu.weboot.bean.User;
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
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.iushu.weboot.component.CacheKeys.*;
import static org.springframework.util.StringUtils.endsWithIgnoreCase;
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

    private static final String KEY_USER = "wbu";
    private static final String KEY_USER_ID = "wbuid";
    private static final String KEY_TOKEN = "wbtkn";

    private static final int COOKIE_EXPIRE_DAYS = 24 * 60 * 60;  // 1 day

    private ApplicationContext applicationContext;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(KEY_USER);
    }

    public User getLoggedUser(HttpServletRequest request, HttpServletResponse response) {
        String loggedToken;
        String token = (String) request.getSession().getAttribute(KEY_TOKEN);
        User user = (User) request.getSession().getAttribute(KEY_USER);
        if (user != null && isEmpty(token)) {
            loggedToken = (String) redisTemplate.opsForHash().get(H_TOKEN, user.getUserId());
            return token.equals(loggedToken) ? user : null;
        }

        Cookie userIdCookie = WebUtils.getCookie(request, KEY_USER_ID);
        Cookie tokenCookie = WebUtils.getCookie(request, KEY_TOKEN);
        if (userIdCookie == null || tokenCookie == null)
            return null;

        // auto-login and sso
        token = tokenCookie.getValue();
        String userId = userIdCookie.getValue();
        loggedToken = (String) redisTemplate.opsForHash().get(H_TOKEN, userId);
        if (loggedToken == null || !loggedToken.equals(token))  // token expired
            return null;

        user = (User) redisTemplate.opsForHash().get(H_STAFF, userId);
        if (user == null || !userId.equals(Short.toString(user.getUserId())))         // illegal access
            return null;

        user.setPassword("");
        manageCookie(response, token, user.getUserId(), COOKIE_EXPIRE_DAYS);
        request.getSession().setAttribute(KEY_TOKEN, token);
        request.getSession().setAttribute(KEY_USER, user);
        request.getSession().setAttribute(KEY_USER_ID, user.getUserId());
        applicationContext.publishEvent(new UserLoginEvent(user));
        return user;
    }

    public User login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        User user = staffService.getUser(username);
        if (user == null)
            return null;

        if (!password.equals(user.getPassword()))
            return PASSWORD_WRONG;

        // remove old
        String userId = Short.toString(user.getUserId());
        String token = (String) redisTemplate.opsForHash().get(H_TOKEN, userId);
        if (!isEmpty(token))
            redisTemplate.opsForHash().delete(H_TOKEN, userId);

        // add new
        String raw = user.getUserId() + user.getUsername() + user.getPassword() + System.currentTimeMillis();
        token = DigestUtils.md5DigestAsHex(raw.getBytes());
        redisTemplate.opsForHash().put(H_STAFF, userId, user);
        redisTemplate.opsForHash().put(H_TOKEN, userId, token);

        user.setPassword("");
        manageCookie(response, token, user.getUserId(), COOKIE_EXPIRE_DAYS);
        request.getSession().setAttribute(KEY_TOKEN, token);
        request.getSession().setAttribute(KEY_USER, user);
        request.getSession().setAttribute(KEY_USER_ID, user.getUserId());
        applicationContext.publishEvent(new UserLoginEvent(user));
        return user;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        short userId = (short) request.getSession().getAttribute(KEY_USER_ID);
        String token = (String) request.getSession().getAttribute(KEY_TOKEN);
        manageCookie(response, token, userId, 0);    // delete cookies

        redisTemplate.opsForHash().delete(H_STAFF, userId);
        redisTemplate.opsForHash().delete(H_TOKEN, userId);
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
