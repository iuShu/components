package org.iushu.declarative;

import org.aopalliance.intercept.MethodInvocation;
import org.iushu.declarative.bean.Staff;
import org.iushu.declarative.service.StaffService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.iushu.concepts.Application.checkComponents;

/**
 * @author iuShu
 * @since 3/22/21
 */
public class Application {

    /**
     * The declarative transaction support are enabled via AOP proxies.
     * @see #annotationBased() working principle described in method doc.
     */
    static void declarative() {
        String configPath = "org/iushu/declarative/spring-declarative.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        StaffService staffService = context.getBean(StaffService.class);
        Staff staff = staffService.getStaff(22);
        System.out.println(staff);

        checkComponents((AbstractApplicationContext) context);
    }

    /**
     * NOTE: The Spring team Recommends that only annotate concrete classes with @Transactional,
     *       annotated to interfaces would lose class-based proxy attribute.
     *
     * NOTE: In 'proxy' mode, only external method calls coming in through the proxy are intercepted,
     *       it means that internal method calls does not lead to an actual transaction at runtime
     *       even if the invoked method is marked with @Transactional.
     *       considering using 'aspectj' mode(requires spring-aspectj.jar) for any method calls, and
     *       require annotate at the implementation class not the interfaces in aspectj mode.
     *
     * NOTE: Annotated @Transactional at method has precedence over annotated at class level.
     *
     * Annotation-based
     * @see DeclarativeConfiguration
     * @see org.springframework.transaction.annotation.Transactional
     * @see org.springframework.transaction.annotation.EnableTransactionManagement#mode() proxy/aspectj
     *
     * @see org.springframework.aop.config.AopConfigUtils register built-in components
     * @see org.springframework.transaction.config.TransactionManagementConfigUtils config key
     * @see org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator collecting advisores for infrastructure
     * @see org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration
     *
     * @see org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor mode="proxy"
     * @see org.springframework.transaction.config.TransactionManagementConfigUtils#TRANSACTION_ASPECT_CLASS_NAME mode="aspectj"
     * @see org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
     * @see org.springframework.transaction.interceptor.TransactionInterceptor
     * @see org.springframework.transaction.event.TransactionalEventListenerFactory
     *
     * How Spring get a connection in Aspect and bind to current thread.
     * @see TransactionInterceptor#invoke(MethodInvocation)
     * @see TransactionAspectSupport#invokeWithinTransaction
     * @see TransactionAspectSupport#determineTransactionManager(TransactionAttribute) get TransactionManager from BeanFactory
     * @see TransactionAspectSupport#createTransactionIfNecessary
     * @see DataSourceTransactionManager#getTransaction(TransactionDefinition)
     * @see DataSourceTransactionManager#doBegin(Object, TransactionDefinition) getConnection from DataSource
     * @see TransactionSynchronizationManager#bindResource(Object, Object) bind connection to current thread
     * @see TransactionAspectSupport.InvocationCallback#proceedWithInvocation() invoke client's method
     * @see TransactionAspectSupport#commitTransactionAfterReturning commit
     * @see TransactionAspectSupport#completeTransactionAfterThrowing rollback or commit
     */
    static void annotationBased() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DeclarativeConfiguration.class);
        context.refresh();

        StaffService staffService = context.getBean(StaffService.class);
        Staff staff = staffService.getStaff(22);
        System.out.println(staff);

        checkComponents(context);
        context.close();
    }

    public static void main(String[] args) {
//        supportedBy();
//        declarative();
        annotationBased();
    }

}
