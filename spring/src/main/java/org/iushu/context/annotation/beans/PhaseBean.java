package org.iushu.context.annotation.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author iuShu
 * @since 7/7/21
 */
@Component
public class PhaseBean implements BeanFactoryAware, ApplicationContextAware, InitializingBean, BeanPostProcessor {

    @Autowired
    private Egg egg;

    @Value("${jdbc.user}")
    private String user;

    static {
        System.out.println("[static] execute");
    }

    public PhaseBean() {
        System.out.println("[PhaseBean] constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[PreDestroy] destroy");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("[BeanFactoryAware] setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("[ApplicationContextAware] setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("[InitializingBean] afterPropertiesSet");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor] BEFORE " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor] AFTER " + beanName);
        return bean;
    }
}
