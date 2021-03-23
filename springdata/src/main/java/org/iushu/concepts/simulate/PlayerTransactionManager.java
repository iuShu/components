package org.iushu.concepts.simulate;

import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.sql.Savepoint;

/**
 * @author iuShu
 * @since 3/23/21
 */
public class PlayerTransactionManager extends AbstractPlatformTransactionManager {

    private SavepointManager savepointManager;
    private Savepoint savepoint;

    public PlayerTransactionManager(SavepointManager savepointManager) {
        this.savepointManager = savepointManager;
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        return savepointManager;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        PlayerTransactionObject playerTransaction = (PlayerTransactionObject) transaction;
        GameChapter.SafeHouse safeHouse = (GameChapter.SafeHouse) playerTransaction.createSavepoint();
        playerTransaction.releaseSavepoint(savepoint);  // release old
        this.savepoint = safeHouse;
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        if (status.isRollbackOnly() || status.isCompleted())
            return;

        PlayerTransactionObject playerTransaction = (PlayerTransactionObject) status.getTransaction();
        GameChapter.SafeHouse safeHouse = (GameChapter.SafeHouse) savepoint;

        GameChapter chapter = playerTransaction.getChapter();
        int newProgress = chapter.getProgress() + safeHouse.getProgress();
        chapter.setProgress(newProgress);

        playerTransaction.flush();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        if (status.isCompleted())
            return;

        PlayerTransactionObject playerTransaction = (PlayerTransactionObject) status.getTransaction();
        playerTransaction.rollbackToSavepoint(savepoint);
    }

}
