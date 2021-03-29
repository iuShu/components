package org.iushu.workflow;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.Set;

/**
 * @author iuShu
 * @since 3/29/21
 */
public class Application {

    /**
     * Catalina component's lifecycle start and trigger the listener configured at web.xml.
     * Traditional xml-based startup way.
     *
     * @see javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent)
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(ServletContextEvent)
     * @see org.springframework.web.context.ContextLoader#initWebApplicationContext(ServletContext)
     * @see org.springframework.web.context.ContextLoader#determineContextClass(ServletContext)
     *
     * @see org.springframework.web.context.support.XmlWebApplicationContext default WebApplicationContext
     * @see org.springframework.web.context.support.StandardServletEnvironment default env
     *
     * @see org.springframework.web.context.ContextLoader#configureAndRefreshWebApplicationContext
     * @see org.springframework.context.support.AbstractApplicationContext#refresh()
     */
    static void workflow() {

    }

    /**
     * Implementation of javax.servlet.ServletContainerInitializer will be detected automatically by
     * any Servlet 3.0 container. The annotation-base way as opposed to the traditional xml-based way.
     * NOTE: ServletContainerInitializer is trigger earlier than ServletContextListener.
     *
     * @see javax.servlet.annotation.HandlesTypes indicates which class the ServletContainerInitializer will handling.
     * @see javax.servlet.ServletContainerInitializer SPI and services file located at spring-web module.
     * @see org.springframework.web.WebApplicationInitializer an alternative initializer for ContextLoaderListener.
     *
     * @see javax.servlet.ServletContainerInitializer#onStartup(Set, ServletContext)
     * @see org.springframework.web.SpringServletContainerInitializer#onStartup(Set, ServletContext)
     */
    static void servlet3Startup() {

    }

    /**
     * @see ConfigurableWebApplicationContext an extension and core ApplicationContext interface at SpringMVC
     * @see org.springframework.web.context.support.AbstractRefreshableWebApplicationContext core implementation
     *
     * @see org.springframework.web.context.support.XmlWebApplicationContext xml-based
     * @see org.springframework.web.context.support.AnnotationConfigWebApplicationContext annotation-based
     */
    static void webApplicationContext() {

    }

    /**
     * Default components are configured at org/springframework/web/server/DispatcherServlet.properties
     * @see org.springframework.web.servlet.HandlerMapping
     * @see org.springframework.web.servlet.HandlerAdapter
     * @see org.springframework.web.servlet.HandlerExceptionResolver
     * @see org.springframework.web.servlet.ViewResolver
     * @see org.springframework.web.servlet.ThemeResolver
     * @see org.springframework.web.servlet.LocaleResolver
     */
    static void dispatchServletComponents() {

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

    }

}
