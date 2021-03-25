package org.iushu.concepts.event;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author iuShu
 * @since 3/25/21
 */
@Aspect
public class FocusTransactionalEventListenerMethod {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Before("bean(*Service)")
    public void publishEventForRegisterTransactionListener(JoinPoint joinPoint) {
        eventPublisher.publishEvent(joinPoint.getTarget());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(ApplicationEvent event) {
        System.out.println("[before-commit] " + event.getClass().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(ApplicationEvent event) {
        System.out.println("[after-commit] " + event.getClass().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollback(ApplicationEvent event) {
        System.out.println("[after-rollback] " + event.getClass().getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletion(ApplicationEvent event) {
        System.out.println("[after-completion] " + event.getClass().getName());
    }

}
