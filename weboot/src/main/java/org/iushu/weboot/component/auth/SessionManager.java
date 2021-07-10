package org.iushu.weboot.component.auth;

import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.CacheKeys;
import org.iushu.weboot.event.UserLoginEvent;
import org.iushu.weboot.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static org.iushu.weboot.component.CacheKeys.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Component
public class SessionManager implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    public static final User PASSWORD_WRONG = new User();
    public static final User RE_LOGIN = new User();

    private static final String KEY_USER = "wbu";
    private static final String KEY_USER_ID = "wbuid";
    private static final String KEY_TOKEN = "wbtkn";

    private static final int SESSION_EXPIRE_MILLIS = 2 * 60 * 60 * 1000;  // 2 hours
    private static final int COOKIE_EXPIRE_SEC = 8 * 60 * 60;  // 8 hours

    private ApplicationContext applicationContext;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(KEY_USER);
    }

    public User getLoggedUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(KEY_USER);
        String token = (String) session.getAttribute(KEY_TOKEN);
        if (user != null && !isEmpty(token)) {
            String cacheKey = userKey(user.getUserId());
            String cacheToken = (String) redisTemplate.opsForHash().get(cacheKey, TOKEN);
            if (!isEmpty(cacheToken)) {
                if (token.equals(cacheToken)) {
                    redisTemplate.expire(cacheKey, ofSeconds(SESSION_EXPIRE_MILLIS));
                    return user;
                }
                return RE_LOGIN;
            }
        }

        Cookie tokenCookie = WebUtils.getCookie(request, KEY_TOKEN);
        if (tokenCookie == null)
            return null;
        String cookieToken = tokenCookie.getValue();
        if (isEmpty(cookieToken))
            return null;

        // auto-login or sso (same domain)
        user = authenticationManager.decryptToken(cookieToken);
        if (user == null)   // illegal request
            return null;
        return login(request, response, user.getUsername(), user.getPassword());
    }

    public User login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        User user = staffService.getUser(username);
        if (user == null)
            return null;

        if (!password.equals(user.getPassword()))
            return PASSWORD_WRONG;

        String token = authenticationManager.encryptToken(user);
        cacheLoggedUser(user, token);
        manageCookie(response, token, COOKIE_EXPIRE_SEC);

        request.getSession().setAttribute(KEY_TOKEN, token);
        request.getSession().setAttribute(KEY_USER, user);
        request.getSession().setAttribute(KEY_USER_ID, user.getUserId());
        applicationContext.publishEvent(new UserLoginEvent(user));
        return user;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        short userId = (short) session.getAttribute(KEY_USER_ID);
        String token = (String) session.getAttribute(KEY_TOKEN);
        manageCookie(response, token, 0);           // delete cookies
        redisTemplate.delete(CacheKeys.userKey(userId));    // delete cache
        session.removeAttribute(KEY_TOKEN);
        session.removeAttribute(KEY_USER);
        session.removeAttribute(KEY_USER_ID);
    }

    private void cacheLoggedUser(User user, String token) {
        user.setPassword("");
        byte[] cacheKey = userKey(user.getUserId()).getBytes();
        RedisSerializer serializer = redisTemplate.getValueSerializer();
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            connection.hMSet(cacheKey, new ObjectHashMapper().toHash(user));
            connection.hSet(cacheKey, TOKEN.getBytes(), serializer.serialize(token));
            connection.expire(cacheKey, SESSION_EXPIRE_MILLIS);
            return null;
        });
    }

    private void manageCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie tokenCookie = new Cookie(KEY_TOKEN, token);
        tokenCookie.setDomain("iushu.com");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(maxAge);
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
    }

}
