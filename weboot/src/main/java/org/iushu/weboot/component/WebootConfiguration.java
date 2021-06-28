package org.iushu.weboot.component;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.*;
import org.springframework.context.support.AbstractApplicationContext;
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
public class WebootConfiguration implements WebMvcConfigurer {

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
        registry.addInterceptor(new LoginInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
    }
}
