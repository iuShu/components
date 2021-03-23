package org.iushu.concepts;

import org.iushu.concepts.simulate.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.iushu.concepts.simulate.GameWorld.DEFAULT_PLAYER_BLOOD;

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
     * @see org.springframework.transaction.PlatformTransactionManager core interface
     * @see org.springframework.transaction.support.AbstractPlatformTransactionManager core abstract implementation
     */
    static void transactionManager() {

    }

    /**
     * @see org.iushu.concepts.simulate.PlayerTransactionObject
     * @see org.iushu.concepts.simulate.PlayerTransactionManager
     *
     * @see org.springframework.transaction.support.TransactionSynchronization
     * @see org.springframework.transaction.support.TransactionSynchronizationManager
     */
    static void simulate() {
        PlayerTransactionObject transactionObject = GameWorld.instance().startGame();
        PlayerTransactionManager transactionManager = new PlayerTransactionManager(transactionObject);
        TransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(definition);

        Player player = transactionObject.getPlayer();
        GameChapter chapter = transactionObject.getChapter();
        try {
            System.out.println("S " + chapter.getProgress() + " " + player.getBlood());
            while (true) {
                TimeUnit.MILLISECONDS.sleep(300);

                player.battle();

                if (!player.isAlive()) {
                    transactionManager.rollback(status);    // rollback to previous savepoint
                    player.setBlood(DEFAULT_PLAYER_BLOOD);  // rebirth
                    System.out.println("X " + chapter.getProgress() + " " + player.getBlood());

                    // new transaction
                    transactionManager = new PlayerTransactionManager(transactionObject);
                    status = transactionManager.getTransaction(definition);
                }
                else if (chapter.reachSafeHouse()) {
                    transactionManager.commit(status);
                    System.out.println("S " + chapter.getProgress() + " " + player.getBlood());

                    // new transaction
                    transactionManager = new PlayerTransactionManager(transactionObject);
                    status = transactionManager.getTransaction(definition);
                }

                chapter.setProgress(chapter.getProgress() + 1);
                System.out.print("_ ");

                if (chapter.getProgress() >= 100)
                    break;
            }
            System.out.println("S " + chapter.getProgress() + " " + player.getBlood());
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }

    public static void checkComponents(AbstractApplicationContext context) {
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
//        transactionStatus();
        simulate();
    }

}
