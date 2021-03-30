package org.iushu.workflow;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
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
     *
     * DispatcherServlet have added into ServletContext, the next step is the container loading the application.
     * At this time the container is going to initialize the Servlet at this application.
     * @see javax.servlet.GenericServlet#init()
     * @see org.springframework.web.servlet.HttpServletBean#init()
     * @see FrameworkServlet#init()
     * @see FrameworkServlet#initServletBean()
     * @see FrameworkServlet#initWebApplicationContext()
     * @see org.springframework.web.servlet.DispatcherServlet#onRefresh(ApplicationContext)
     * @see org.springframework.web.servlet.DispatcherServlet#initStrategies(ApplicationContext)
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
     *
     * DispatcherServlet have added into ServletContext, the next step is the container loading the application.
     * At this time the container is going to initialize the Servlet at this application.
     * @see javax.servlet.GenericServlet#init()
     * @see org.springframework.web.servlet.HttpServletBean#init()
     * @see FrameworkServlet#init()
     * @see FrameworkServlet#initServletBean()
     * @see FrameworkServlet#initWebApplicationContext()
     * @see org.springframework.web.servlet.DispatcherServlet#onRefresh(ApplicationContext)
     * @see org.springframework.web.servlet.DispatcherServlet#initStrategies(ApplicationContext)
     */
    static void servlet3Startup() {

    }

    /**
     * @see ConfigurableWebApplicationContext an extension and core ApplicationContext interface at SpringMVC
     * @see org.springframework.web.context.support.AbstractRefreshableWebApplicationContext core implementation
     *
     * @see org.springframework.web.context.support.XmlWebApplicationContext xml-based
     * @see org.springframework.web.context.support.AnnotationConfigWebApplicationContext annotation-based
     *
     * Default components of WebMVC have been set up as built-in before(@Bean annotated method).
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
     */
    static void webApplicationContext() {

    }

    /**
     * Default components are configured at org/springframework/web/server/DispatcherServlet.properties
     * @see HandlerMapping
     * @see org.springframework.web.servlet.HandlerAdapter
     * @see org.springframework.web.servlet.HandlerExceptionResolver
     * @see org.springframework.web.servlet.ViewResolver
     * @see org.springframework.web.servlet.ThemeResolver
     * @see org.springframework.web.servlet.LocaleResolver
     */
    static void dispatchServletComponents() {

    }

    /**
     * The processing flow when an request comes in.
     *
     * @see FrameworkServlet#processRequest(HttpServletRequest, HttpServletResponse)
     * @see FrameworkServlet#buildLocaleContext(HttpServletRequest)
     * @see FrameworkServlet#buildRequestAttributes(HttpServletRequest, HttpServletResponse, RequestAttributes)
     * @see FrameworkServlet#initContextHolders(HttpServletRequest, LocaleContext, RequestAttributes)
     * @see LocaleContextHolder#setLocaleContext(LocaleContext, boolean) bind LocaleContext at current thread
     * @see RequestContextHolder#setRequestAttributes(RequestAttributes, boolean) bind RequestAttributes at current thread
     *
     * Binding necessary components on HttpRequest by request.setAttribute(..).
     * @see #dispatchServletComponents() built-in components configuration
     * The key of attributes have listed following:
     * @see org.springframework.web.servlet.DispatcherServlet#WEB_APPLICATION_CONTEXT_ATTRIBUTE
     * @see org.springframework.web.servlet.DispatcherServlet#LOCALE_RESOLVER_ATTRIBUTE
     * @see org.springframework.web.servlet.DispatcherServlet#THEME_RESOLVER_ATTRIBUTE
     * @see org.springframework.web.servlet.DispatcherServlet#THEME_SOURCE_ATTRIBUTE
     *
     * Then turn to core request handling flow, with essential components mentioned above.
     * @see org.springframework.web.servlet.DispatcherServlet#doDispatch
     * @see #requestHandlerMapping() fetch an appropriate HandlerMapping
     * @see #requestHandlerAdapter() the actual operator for HandlerMapping
     */
    static void requestWorkflow() {
        requestHandlerMapping();
        requestHandlerAdapter();
    }

    /**
     * Core components for HandlerMapping.
     * @see HandlerMapping
     * @see org.springframework.web.servlet.HandlerExecutionChain
     * @see AbstractHandlerMethodMapping.MappingRegistry actual action for lookup request path
     * @see org.springframework.web.servlet.mvc.method.RequestMappingInfo metadata for @RequestMapping annotation
     *
     * @see org.springframework.web.servlet.DispatcherServlet#getHandler(HttpServletRequest)
     * @see RequestMappingHandlerMapping#getHandler(HttpServletRequest)
     * @see AbstractHandlerMapping#getHandler(HttpServletRequest) super class of RequestMappingHandlerMapping
     * @see AbstractHandlerMethodMapping#getHandlerInternal(HttpServletRequest)
     * @see AbstractHandlerMethodMapping#lookupHandlerMethod(String, HttpServletRequest) start lookup requesting path
     * @see AbstractHandlerMethodMapping.MappingRegistry#getMappingsByDirectPath(String) path without dynamic parameters
     * @see AbstractHandlerMethodMapping.MappingRegistry#addMatchingMappings(Collection, List, HttpServletRequest) all path collection
     * @see org.springframework.web.servlet.mvc.method.RequestMappingInfo#getMatchingCondition(HttpServletRequest)
     * @see AbstractHandlerMethodMapping.Match indicates a Match of a request and a HandlerMappingMethod
     *
     * Sort and choose a best matching HandlerMapping for request if found multiple matched HandlerMappings.
     * If no conflict situation, binding the best matching handler method to the request
     * @see HandlerMapping#BEST_MATCHING_HANDLER_ATTRIBUTE attribute key
     * @see AbstractHandlerMethodMapping.Match#handlerMethod attribute value
     *
     * @see RequestMappingInfoHandlerMapping#handleMatch
     * @see RequestMappingInfoHandlerMapping#extractMatchDetails extract url parameters for method's arg.
     * @see org.springframework.web.method.HandlerMethod
     * @see org.springframework.web.servlet.HandlerExecutionChain#handler HandlerMethod
     * @see org.springframework.web.servlet.HandlerExecutionChain#addInterceptor(HandlerInterceptor)
     * @see #requestHandlerAdapter()
     */
    static void requestHandlerMapping() {

    }

    /**
     * @see org.springframework.web.servlet.HandlerAdapter supports various HandlerMapping
     */
    static void requestHandlerAdapter() {

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
