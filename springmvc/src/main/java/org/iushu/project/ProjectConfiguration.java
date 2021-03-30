package org.iushu.project;

import org.apache.commons.dbcp2.BasicDataSource;
import org.iushu.project.components.ConnectionMetadata;
import org.iushu.project.components.TraceHandlerInterceptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.sql.DataSource;

/**
 * Supports @EnableWebMvc annotation and
 * @see WebMvcConfigurationSupport
 *
 * @author iuShu
 * @since 3/29/21
 */
@Configuration
@ComponentScan(basePackages = "org.iushu.project.*")
@EnableWebMvc
public class ProjectConfiguration implements WebMvcConfigurer {

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

    /**
     * The HandlerInterceptors needs to adding before ApplicationContext refreshed.
     * @see WebMvcConfigurationSupport#requestMappingHandlerMapping
     * @see WebMvcConfigurationSupport#getInterceptors
     * @see WebMvcConfigurationSupport#addInterceptors(InterceptorRegistry)
     * @see DelegatingWebMvcConfiguration#addInterceptors(InterceptorRegistry)
     * @see WebMvcConfigurer#addInterceptors(InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceHandlerInterceptor());
    }
}
