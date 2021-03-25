package org.iushu.programatic;

import org.apache.commons.dbcp2.BasicDataSource;
import org.iushu.declarative.DeclarativeConfiguration;
import org.iushu.programatic.bean.Actor;
import org.iushu.programatic.service.ActorService;
import org.iushu.programatic.service.DefaultActorService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author iuShu
 * @since 3/24/21
 */
public class Application {

    /**
     * @see org.springframework.transaction.support.TransactionTemplate
     */
    static void transactionTemplate() {
        BasicDataSource dataSource = (BasicDataSource) new DeclarativeConfiguration().dataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        ActorService actorService = new DefaultActorService(dataSource);

        short actor_id = 4;
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        Actor actor = transactionTemplate.execute(status -> actorService.getActor(actor_id));
        System.out.println(actor);

        actor.setLast_name("JAVIS");
        boolean success = transactionTemplate.execute(status -> {
            try {
                return actorService.updateActor(actor);
            } catch (Exception e) {
                status.setRollbackOnly();   // rollback if exception occurred
                return false;
            }
        });

        System.out.println(actor);
        System.out.println(success);
    }

    /**
     * @see org.springframework.transaction.reactive.TransactionalOperator
     */
    static void transactionOperator() {

    }

    /**
     * @see TransactionTemplate#execute(TransactionCallback)
     */
    static void transactionTemplateImplementation() {
        BasicDataSource dataSource = (BasicDataSource) new DeclarativeConfiguration().dataSource();
        PlatformTransactionManager manager = new DataSourceTransactionManager(dataSource);
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

        TransactionStatus status = manager.getTransaction(definition);

        // TransactionCallback.doInTransaction(..)
        // TransactionCallbackWithoutResult.doInTransaction(..)

        // commit or rollback
        manager.rollback(status);
        manager.commit(status);
    }

    public static void main(String[] args) {
//        transactionTemplate();
        transactionOperator();
//        transactionTemplateImplementation();
    }

}
