package org.iushu.weboot.component.interceptor;

import org.iushu.weboot.annotation.AccessLimit;
import org.iushu.weboot.component.AccessLimitProperties;
import org.iushu.weboot.component.CacheKeys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.Duration;

import static org.iushu.weboot.component.CacheKeys.accessLimitKey;

/**
 * restrict server request frequency
 *
 * @author iuShu
 * @since 6/24/21
 */
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    private static final int NO_LIMITED = -1;
    private AccessLimitProperties globalSetting;
    private RedisTemplate<String, Object> redisTemplate;

    public AccessLimitInterceptor(AccessLimitProperties properties, RedisTemplate redisTemplate) {
        this.globalSetting = properties;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;    // proceed

        AccessLimitProperties properties = globalSetting;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
        if (accessLimit != null) {
            properties = new AccessLimitProperties();
            properties.setMilliseconds(accessLimit.duration());
            properties.setFrequency(accessLimit.frequency());
        }

        if (NO_LIMITED == properties.getFrequency() || NO_LIMITED == properties.getMilliseconds())
            return true;    // no limited

        String cacheKey = accessLimitKey(request.getRemoteHost());
        Integer count = (Integer) redisTemplate.opsForValue().get(cacheKey);
        if (count == null) {
            redisTemplate.opsForValue().set(cacheKey, 1, Duration.ofMillis(properties.getMilliseconds()));
            return true;
        }

        if (count < properties.getFrequency()) {
            redisTemplate.opsForValue().increment(cacheKey);
            return true;
        }

        return false;   // exceed limited
    }
}
