package org.iushu.weboot.component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.iushu.weboot.component.auth.SessionManager;
import org.iushu.weboot.component.interceptor.LoginInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Configuration
public class WebootConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
        objectMapper.activateDefaultTyping(polymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(objectMapper);

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * @see java.util.concurrent.ThreadPoolExecutor
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     * @see org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean
     *
     * @see AbstractApplicationContext#initApplicationEventMulticaster()
     * @see AbstractApplicationContext#APPLICATION_EVENT_MULTICASTER_BEAN_NAME
     */
    @EventListener(ContextRefreshedEvent.class)
    public void asyncEventMulticaster(ApplicationContextEvent event) {
        int coreWorker = Runtime.getRuntime().availableProcessors();
        BlockingQueue blockingQueue = new ArrayBlockingQueue(120);
        Executor executor = new ThreadPoolExecutor(coreWorker, coreWorker << 1,
                60, TimeUnit.SECONDS, blockingQueue);

        ApplicationContext context = event.getApplicationContext();
        SimpleApplicationEventMulticaster eventMulticaster = context.getBean(SimpleApplicationEventMulticaster.class);
        eventMulticaster.setTaskExecutor(executor);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SessionManager sessionManager = applicationContext.getBean(SessionManager.class);
        registry.addInterceptor(new LoginInterceptor(sessionManager));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
