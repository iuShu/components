package org.iushu.declarative;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
     * @see org.springframework.transaction.annotation.Transactional
     * @see org.springframework.transaction.annotation.EnableTransactionManagement
     */
    static void supportedBy() {

    }

    public static void main(String[] args) {
        supportedBy();
    }

}
