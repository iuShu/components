package org.iushu.transaction;

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

    public static void main(String[] args) {
        transactionStatus();
    }

}
