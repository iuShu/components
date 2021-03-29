package org.iushu.project;

import org.apache.commons.dbcp2.BasicDataSource;
import org.iushu.project.components.ConnectionMetadata;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Supports @EnableWebMvc annotation and
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
 *
 * @author iuShu
 * @since 3/29/21
 */
@Configuration
@ComponentScan(basePackages = "org.iushu.project.*")
@EnableWebMvc
public class ProjectConfiguration {

    @Bean
    public PropertyResourceConfigurer propertyResourceConfigurer(BeanFactory beanFactory) {
        String filePath = "/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/components/springmvc/web/WEB-INF/mysql.properties";
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setBeanFactory(beanFactory);
        configurer.setLocation(new FileSystemResource(filePath));
        return configurer;
    }

    @Bean
    public DataSource dataSource(ConnectionMetadata metadata) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(metadata.getDriver());
        dataSource.setUrl(metadata.getUrl());
        dataSource.setUsername(metadata.getUser());
        dataSource.setPassword(metadata.getPassword());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
