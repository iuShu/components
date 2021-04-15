package org.iushu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author iuShu
 * @since 4/15/21
 */
@SpringBootApplication
public class JdbcApplication {

    /**
     * @see com.zaxxer.hikari.HikariDataSource
     *
     * AutoConfiguration -> Configuration -> createBean -> BeanPostProcessor
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration import DataSourceInitializationConfiguration
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceInitializationConfiguration.Registrar#registerBeanDefinitions
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari#dataSource imported before
     * @see org.springframework.boot.jdbc.DataSourceBuilder#build() create DataSource by builder
     * @see com.zaxxer.hikari.HikariDataSource instantiated but not initialize yet
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerPostProcessor#postProcessAfterInitialization
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker#afterPropertiesSet()
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceInitializer#initSchema() handle '*.sql' data file
     * @see DataSourcePoolMetadataProvidersConfiguration.HikariPoolDataSourceMetadataProviderConfiguration
     * @see org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider pool metadata provider
     * @see org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata
     *
     * @see org.springframework.boot.jdbc.DatabaseDriver#productName
     * @see org.springframework.boot.jdbc.DatabaseDriver#driverClassName
     * @see org.springframework.boot.jdbc.DatabaseDriver#validationQuery
     */
    static void dataSource(ConfigurableApplicationContext context) {
        DataSource dataSource = context.getBean(DataSource.class);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        System.out.println(dataSource.getClass().getName());
        System.out.println(connection.getClass().getName());
    }

    /**
     * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
     * @see org.mybatis.spring.boot.autoconfigure.MybatisProperties
     * @see org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer
     */
    static void mybatisConfiguration() {

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JdbcApplication.class);

        dataSource(context);

        org.iushu.web.Application.checkComponents((AbstractApplicationContext) context);
    }

}
