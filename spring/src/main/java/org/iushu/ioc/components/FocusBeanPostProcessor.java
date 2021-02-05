package org.iushu.ioc.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;

/**
 * Effective even in initialing itself.
 *
 * @author iuShu
 * @since 12/31/20
 */
public class FocusBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    /**
     * ATTENTION: It will not invoke during initialing a bean stemmed from FactoryBean
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[before] " + beanName + "\t" + bean.getClass().getName());
        return null;
    }

    /**
     * ATTENTION: Invoke during initialing a bean stemmed from FactoryBean
     * @param bean bean or bean stemmed from FactoryBean
     * @param beanName bean's name or the FactoryBean's name (not the name of bean's stemmed from FactoryBean)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("[after] " + beanName + "\t" + bean.getClass().getName());
        return null;
    }

    /**
     * Register itself into BeanFactory or by other class which contains a BeanFactory.
     *
     * ATTENTION:
     * this method only be invoked during BeanFactory#getBean(),
     * it will not applied on targets at all unless initiative invoke.
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AbstractBeanFactory abstractBeanFactory = (AbstractBeanFactory) beanFactory;
        abstractBeanFactory.addBeanPostProcessor(this);
    }
}
