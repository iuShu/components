package org.iushu.declarative;

import org.apache.commons.dbcp2.BasicDataSource;
import org.iushu.ConnectionMetaData;
import org.iushu.declarative.service.DefaultStaffService;
import org.iushu.declarative.service.StaffService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author iuShu
 * @since 3/24/21
 */
@Configuration
@EnableTransactionManagement
public class DeclarativeConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StaffService staffService() {
        return new DefaultStaffService();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(ConnectionMetaData.JDBC_DRIVER);
        dataSource.setUrl(ConnectionMetaData.JDBC_URL);
        dataSource.setUsername(ConnectionMetaData.JDBC_USER);
        dataSource.setPassword(ConnectionMetaData.JDBC_PASSWORD);
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
