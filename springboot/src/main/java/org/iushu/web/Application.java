package org.iushu.web;

import org.apache.catalina.startup.Tomcat;
import org.iushu.web.component.WebXmlConfiguration;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * @author iuShu
 * @since 4/6/21
 */
@EnableAutoConfiguration
@ComponentScan("org.iushu.web")
public class Application {

    /**
     * NOTE: Annotated with @EnableWebMvc at Configuration class means invalid the auto-configured components.
     * @see org.springframework.web.servlet.config.annotation.EnableWebMvc
     * @see org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport only contains basic configuration
     *
     * Spring boot auto-configuration had annotated with @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
     * That means it only valid at the context that not contain anyother WebMvcConfigurationSupport bean.
     * @see WebMvcAutoConfiguration adding Springboot requires components for SpringMVC
     *
     * SpringMvc auto configuration components:
     * @see WebMvcAutoConfiguration
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
     *
     * Sample components:
     * @see org.springframework.web.servlet.ViewResolver
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver
     * @see org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver
     *
     * @see org.springframework.http.converter.HttpMessageConverter
     * @see org.springframework.boot.autoconfigure.http.HttpMessageConverters
     *
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#resourceHandlerRegistrationCustomizer
     * @see org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
     * @see #resourceHandler() for more details
     */
    static void mvcConfiguration() {

    }

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
     * Static templates for template engines like Freemarker and Thymeleaf: resources/templates/*
     */
    static void resourceHandler() {

    }

    /**
     * @see EmbeddedWebServerFactoryCustomizerAutoConfiguration
     * @see org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor
     * @see WebXmlConfiguration#customizer(ServerProperties) for more details
     *
     * @see org.springframework.boot.web.server.WebServerFactoryCustomizer
     * @see org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer
     * @see org.springframework.boot.autoconfigure.web.embedded.JettyWebServerFactoryCustomizer
     * @see org.springframework.boot.autoconfigure.web.embedded.UndertowWebServerFactoryCustomizer
     *
     * @see org.springframework.boot.web.server.ConfigurableWebServerFactory
     * @see org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory
     * @see org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
     * @see org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory
     *
     * @see org.springframework.boot.web.server.WebServer
     * @see org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory#getWebServer
     * @see org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory#getTomcatWebServer(Tomcat)
     * @see org.springframework.boot.web.embedded.tomcat.TomcatWebServer#initialize()
     * @see org.apache.catalina.startup.Tomcat#start()
     */
    static void webServer() {

    }

    /**
     * @see org.springframework.boot.web.servlet.ServletRegistrationBean
     * @see org.springframework.boot.web.servlet.FilterRegistrationBean
     * @see org.springframework.boot.web.servlet.ServletListenerRegistrationBean
     * @see org.springframework.boot.web.servlet.ServletContextInitializer parent interface
     *
     * @see WebXmlConfiguration#pumpkinServlet()
     * @see WebXmlConfiguration#pumpkinFilter()
     */
    static void servletCoreComponents() {

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
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

//        System.out.println(context.getClass().getName());
        checkComponents((AbstractApplicationContext) context);
    }

}
