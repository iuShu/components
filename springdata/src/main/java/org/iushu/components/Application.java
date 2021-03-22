package org.iushu.components;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * @author iuShu
 * @since 3/4/21
 */
public class Application {

    /**
     * @see org.springframework.transaction.SavepointManager
     * @see org.springframework.transaction.TransactionStatus
     * @see org.springframework.transaction.support.DefaultTransactionStatus
     *
     * @see SimpleTransactionStatus for custom a transaction manager or as a static mock in testing
     */
    static void transactionStatus() {
//        TransactionStatus transactionStatus = new SimpleTransactionStatus();
//        transactionStatus.createSavepoint();  // not supports savepoint

//        TransactionStatus transactionStatus = new DefaultTransactionStatus();
    }

    /**
     * @see org.springframework.transaction.TransactionDefinition
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED
     * @see org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT
     */
    static void transactionDefinition() {

    }

    /**
     * @see org.springframework.transaction.TransactionManager
     * @see org.springframework.transaction.PlatformTransactionManager
     * @see org.springframework.transaction.support.AbstractPlatformTransactionManager
     *
     * @see org.springframework.transaction.ReactiveTransactionManager
     */
    static void transactionManager() {

    }

    static void checkComponents(AbstractApplicationContext context) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        System.out.println("Environment: " + context.getEnvironment().getClass().getName());

        for (BeanFactoryPostProcessor processor : context.getBeanFactoryPostProcessors())
            System.out.println("BeanFactoryPostProcessor: " + processor.getClass().getName());
        for (BeanPostProcessor processor : beanFactory.getBeanPostProcessors())
            System.out.println("BeanPostProcessor: " + processor.getClass().getName());
        for (ApplicationListener listener : context.getApplicationListeners())
            System.out.println("ApplicationListener: " + listener.getClass().getName());
        for (ProtocolResolver resolver : context.getProtocolResolvers())
            System.out.println("ProtocolResolver: " + resolver.getClass().getName());
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("Bean: " + name);
            System.out.println("      " + context.getBean(name).getClass().getName());
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void main(String[] args) {
        transactionStatus();
    }

}
