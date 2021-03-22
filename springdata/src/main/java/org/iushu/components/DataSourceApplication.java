package org.iushu.components;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

/**
 * @author iuShu
 * @since 3/22/21
 */
public class DataSourceApplication {

    /**
     * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
     */
    static void JDBCTransactionManager() {
        ApplicationContext context = new ClassPathXmlApplicationContext("org/iushu/spring-jdbc.xml");

        DataSource dataSource = context.getBean(DataSource.class);

        TransactionDefinition txDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        DataSourceTransactionManager txManager = (DataSourceTransactionManager) context.getBean(TransactionManager.class);
        DefaultTransactionStatus txStatus = (DefaultTransactionStatus) txManager.getTransaction(txDefinition);
        Object transaction = txStatus.getTransaction();
        System.out.println(transaction.getClass().getName());

        Application.checkComponents((AbstractApplicationContext) context);
    }

    /**
     * @see org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
     */
    static void transactioAwareDataSourceProxy() {

    }

    public static void main(String[] args) {
        JDBCTransactionManager();
    }

}
