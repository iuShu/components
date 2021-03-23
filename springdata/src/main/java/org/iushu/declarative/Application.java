package org.iushu.declarative;

import org.iushu.declarative.bean.Staff;
import org.iushu.declarative.service.StaffService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static org.iushu.concepts.Application.checkComponents;

/**
 * @author iuShu
 * @since 3/22/21
 */
public class Application {

    /**
     * The declarative transaction support are enabled via AOP proxies.
     * @see org.springframework.transaction.interceptor.TransactionInterceptor
     * @see TransactionAspectSupport#invokeWithinTransaction
     * @see TransactionAspectSupport.InvocationCallback#proceedWithInvocation()
     * @see TransactionAspectSupport#completeTransactionAfterThrowing
     * @see TransactionAspectSupport#commitTransactionAfterReturning
     *
     * Annotation-based
     * @see org.springframework.transaction.annotation.Transactional
     * @see org.springframework.transaction.annotation.EnableTransactionManagement
     */
    static void declarative() {
        String configPath = "org/iushu/declarative/spring-declarative.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        StaffService staffService = context.getBean(StaffService.class);
        Staff staff = staffService.getStaff(22);
        System.out.println(staff);

        checkComponents((AbstractApplicationContext) context);
    }

    public static void main(String[] args) {
//        supportedBy();
        declarative();
    }

}
