package org.iushu.project;

import org.apache.commons.dbcp2.BasicDataSource;
import org.iushu.project.components.ConnectionMetadata;
import org.iushu.project.components.TraceHandlerInterceptor;
import org.iushu.project.controller.ActorRouterHandler;
import org.iushu.project.service.ActorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.sql.DataSource;

/**
 * Supports @EnableWebMvc annotation and WebMvcConfigurer.
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
     * WebMvc.fn
     * @see ActorRouterHandler
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(ActorService service) {
        ActorRouterHandler handler = new ActorRouterHandler(service);
        RouterFunction<ServerResponse> router = RouterFunctions.route()
                .GET("/router/parameter", RequestPredicates.accept(MediaType.TEXT_HTML), handler::parameters)
                .POST("/router/actor/body", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::bodyActor)
                .GET("/router/actor/filter/{actor_id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getActor)
                .GET("/router/actor/list/{pageNo}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::listActor)

                /* routers */
//                .add(otherRouter)

                /* nested routers */
//                .path("/trace", builder -> {
//                    builder.GET("/model/transport", RequestPredicates.accept(MediaType.TEXT_HTML)), controller::modelFlow)
//                    builder.GET("/model/global", RequestPredicates.accept(MediaType.TEXT_HTML)), controller::globalModel)
//                })

                .build();
        return router;
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

    /**
     * TODO upgrade to path regex matching pattern approach.
     * TODO apply lastModified attribute of the resource.
     *
     * @see WebMvcConfigurationSupport#resourceHandlerMapping
     * @see WebMvcConfigurer#addResourceHandlers(ResourceHandlerRegistry)
     * @see ResourceHandlerRegistry#getHandlerMapping()
     * @see org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
     *
     * handle resource request if url matched
     * @see org.springframework.web.servlet.resource.ResourceHttpRequestHandler
     * @see ResourceHttpRequestHandler#afterPropertiesSet() initialize the handler and prepare resources
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html", "/js/index.js")
                .addResourceLocations("index.html", "/js/index.js")
                .setUseLastModified(true);
    }

    /**
     * Support @MatrixVariable
     * @see org.iushu.project.controller.TraceController#matrixVariable(int, String, int)
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
