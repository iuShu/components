package org.iushu.concepts.event;

import org.iushu.declarative.DeclarativeConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author iuShu
 * @since 3/25/21
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class EventConfiguration {

    @Bean
    public DataSource dataSource() {
        return new DeclarativeConfiguration().dataSource();
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
