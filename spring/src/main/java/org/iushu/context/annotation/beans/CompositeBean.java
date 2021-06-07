package org.iushu.context.annotation.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

/**
 * @author iuShu
 * @since 6/7/21
 */
@Component
public class CompositeBean implements BeanFactoryAware, ApplicationContextAware, InitializingBean,
        BeanPostProcessor, InstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor,
        SmartInstantiationAwareBeanPostProcessor {

    static {
        System.out.println("[static] execute");
    }

    public CompositeBean() {
        System.out.println("[CompositeBean] constructor");
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

    // BeanPostProcessor

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor] postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[BeanPostProcessor] postProcessBeforeInitialization");
        return bean;
    }

    // MergedBeanDefinitionPostProcessor

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        System.out.println("[MergedBeanDefinitionPostProcessor] postProcessMergedBeanDefinition");
    }

    @Override
    public void resetBeanDefinition(String beanName) {
        System.out.println("[MergedBeanDefinitionPostProcessor] resetBeanDefinition");
    }

    // InstantiationAwareBeanPostProcessor

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("[InstantiationAwareBeanPostProcessor] postProcessBeforeInstantiation");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("[InstantiationAwareBeanPostProcessor] postProcessAfterInstantiation");
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
//        System.out.println("[InstantiationAwareBeanPostProcessor] postProcessProperties");
        return pvs;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
//        System.out.println("[InstantiationAwareBeanPostProcessor] postProcessPropertyValues");
        return pvs;
    }

    // SmartInstantiationAwareBeanPostProcessor

    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("[SmartInstantiationAwareBeanPostProcessor] predictBeanType");
        return null;
    }

    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("[SmartInstantiationAwareBeanPostProcessor] determineCandidateConstructors");
        return null;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
//        System.out.println("[SmartInstantiationAwareBeanPostProcessor] getEarlyBeanReference");
        return bean;
    }
}
