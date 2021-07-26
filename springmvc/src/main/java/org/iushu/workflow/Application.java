package org.iushu.workflow;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.*;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.support.HandlerFunctionAdapter;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
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
     * DispatcherServlet has added into ServletContext, the next step is the container loading the application.
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
     * Default components are configured at org/springframework/web/servlet/DispatcherServlet.properties
     * @see org.springframework.web.servlet.HandlerMapping
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
     * @see #dispatchServletComponents() default HandlerAdapter configuration
     * @see org.springframework.web.servlet.HandlerAdapter supports various HandlerMapping
     * @see org.springframework.web.servlet.HandlerAdapter#handle method to handle a request
     *
     * Generally for RequestMapping method, supports by:
     * @see RequestMappingHandlerAdapter supports HandlerMethod (core)
     * @see SimpleControllerHandlerAdapter supports Controller
     * @see HttpRequestHandlerAdapter supports HttpRequestHandler
     * @see HandlerFunctionAdapter
     * @see SimpleServletHandlerAdapter supports Servlet
     *
     * Components at RequestMappingHandlerAdapter
     * @see RequestMappingHandlerAdapter#afterPropertiesSet() apply initialize
     * @see RequestMappingHandlerAdapter#initControllerAdviceCache() init advices of controller
     * @see org.springframework.web.bind.annotation.ControllerAdvice
     * @see org.springframework.web.method.ControllerAdviceBean metadata of the ControllerAdvice
     * @see HandlerMethodArgumentResolver 26 built-in resolvers added during startup
     * @see HandlerMethodArgumentResolverComposite combine argument resolvers
     * @see HandlerMethodReturnValueHandler 15 built-in handlers added during startup
     * @see HandlerMethodReturnValueHandlerComposite combine return value handlers
     *
     * @see RequestMappingHandlerAdapter#handleInternal
     * @see RequestMappingHandlerAdapter#invokeHandlerMethod combine many components before invocation
     * @see ServletInvocableHandlerMethod#invokeAndHandle an HandlerMethod
     * @see InvocableHandlerMethod#invokeForRequest
     * @see InvocableHandlerMethod#getMethodArgumentValues
     * @see InvocableHandlerMethod#doInvoke invoke real action method
     * @see HandlerMethodReturnValueHandlerComposite#handleReturnValue
     * @see RequestResponseBodyMethodProcessor supports @ResponseBody (in this case)
     * @see GsonHttpMessageConverter#write(Object, MediaType, HttpOutputMessage)
     * @see GsonHttpMessageConverter#writeInternal(Object, Type, java.io.Writer)
     *
     * After return data is written back
     * @see RequestMappingHandlerAdapter#getModelAndView
     */
    static void requestHandlerAdapter() {

    }

    /**
     * Parameter binding at handler method
     * @see org.iushu.project.controller.TraceController
     */
    static void parameterBinding() {

    }

    /**
     * @see RequestMappingHandlerAdapter#getModelAndView
     * @see org.springframework.web.servlet.DispatcherServlet#render
     * @see org.springframework.web.servlet.DispatcherServlet#viewResolvers
     * @see org.springframework.web.servlet.view.ViewResolverComposite
     * @see org.springframework.web.servlet.view.InternalResourceViewResolver
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#REDIRECT_URL_PREFIX
     * @see javax.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse) forward to new View
     */
    static void viewResolver() {

    }

    /**
     * @see org.springframework.web.context.request.RequestAttributes
     * @see org.springframework.web.context.request.WebRequest
     * @see org.springframework.web.context.request.NativeWebRequest
     * @see org.springframework.web.context.request.ServletWebRequest
     * @see org.springframework.web.servlet.handler.DispatcherServletWebRequest
     */
    static void springWebRequest() {

    }

    /**
     * @see ServletContext#getResource(String) root directory starts from app/.. (include /WEB-INF)
     * @see ClassPathResource#getPath() root directory starts from app/WEB-INF/classes/..
     */
    static void applicationPath() {

    }

    /**
     * @see ServletContext
     * @see ServletContextAware
     * @see ServletContextAwareProcessor an BeanPostProcessor
     */
    static void servletContextAware() {

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

    public static void checkCookies(Cookie[] cookies) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (cookies == null)
            System.out.println("checkCookies: NULL");
        else if (cookies.length == 0)
            System.out.println("checkCookies: EMPTY");
        else {
            for (Cookie cookie : cookies)
                System.out.println("checkCookies: " + String.format("name: %s, value: %s, path: %s, domain: %s, maxAge: %s",
                        cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(), cookie.getMaxAge()));
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void main(String[] args) {

    }

}
