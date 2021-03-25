package org.iushu.concepts.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalApplicationListener;

/**
 * @author iuShu
 * @since 3/25/21
 */
public class FocusTransactionalEventListener implements TransactionalApplicationListener {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public TransactionPhase getTransactionPhase() {
        return null;
    }

    @Override
    public String getListenerId() {
        return null;
    }

    @Override
    public void addCallback(SynchronizationCallback callback) {

    }

    @Override
    public void processEvent(ApplicationEvent event) {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }
}
