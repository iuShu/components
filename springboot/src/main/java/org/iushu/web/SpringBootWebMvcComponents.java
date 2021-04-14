package org.iushu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static org.iushu.web.Application.checkComponents;

/**
 * Core auto-configuration class
 * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
 *
 * @author iuShu
 * @since 4/13/21
 */
@SpringBootApplication
public class SpringBootWebMvcComponents {

    /**
     * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration
     * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration#welcomePageHandlerMapping
     *
     * Default 'webjars' configuration location: /webjars/** and classpath:/META-INF/resources/webjars/
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#addResourceHandlers
     *
     * Default static resource locating under this directories:
     * @see ResourceProperties#CLASSPATH_RESOURCE_LOCATIONS
     *
     * Locating welcome page 'index.html' under classpath
     * @see org.springframework.boot.autoconfigure.web.servlet.WelcomePageHandlerMapping
     *
     * Custom static resource mapped configuration: spring.mvc.static-path-pattern=..
     * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties#staticPathPattern
     *
     * Content negotiating like path matching, content type customizing.
     * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.Contentnegotiation
     */
    static void staticResource() {

    }

    /**
     * Resolve view by analyzing the content type of the request header, then return appropriate view.
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver#resolveViewName(String, Locale)
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver#getMediaTypes(HttpServletRequest)
     * @see org.springframework.web.accept.ContentNegotiationManager#resolveMediaTypes(NativeWebRequest)
     * @see org.springframework.web.accept.ContentNegotiationStrategy#resolveMediaTypes(NativeWebRequest)
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver#getCandidateViews(String, Locale, List)
     *
     * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration#mvcContentNegotiationManager() auto-configuration
     * @see WebMvcConfigurationSupport#mvcContentNegotiationManager() auto-configuration
     * @see ContentNegotiationConfigurer#buildContentNegotiationManager() build manager
     * @see org.springframework.web.accept.ContentNegotiationManagerFactoryBean factory build
     * @see org.springframework.web.accept.ContentNegotiationManager
     * @see org.springframework.web.accept.ContentNegotiationStrategy
     */
    static void contentNegotiatingViewResolver() {
        ApplicationContext context = SpringApplication.run(SpringBootWebMvcComponents.class);
        checkComponents((AbstractApplicationContext) context);
    }

    public static void main(String[] args) {
        contentNegotiatingViewResolver();
    }

}
