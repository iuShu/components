package org.iushu.context;

import org.aopalliance.intercept.MethodInvocation;
import org.iushu.context.annotation.FocusConfiguration;
import org.iushu.context.annotation.beans.CompositeBean;
import org.iushu.context.annotation.beans.PhaseBean;
import org.iushu.context.beans.*;
import org.iushu.context.components.FocusApplicationEventPublisherAware;
import org.iushu.context.components.FocusApplicationListener;
import org.iushu.context.components.FocusAsyncApplicationListener;
import org.iushu.context.components.GracefulApplicationContext;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.DefaultEventListenerFactory;
import org.springframework.context.event.EventListenerMethodProcessor;
import org.springframework.context.support.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.w3c.dom.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author iuShu
 * @since 1/22/21
 */
public class Application {

    /**
     * BeanFactory ApplicationContext Feature
     * Yes         Yes                Bean instantiation/wiring
     * No          Yes                Integrated Lifecycle management
     * No          Yes                Automatic BeanPostProcessor registration
     * No          Yes                Automatic BeanFactoryPostProcessor registration
     * No          Yes                Convenient MessageSource access
     * No          Yes                Built-in ApplicationEvent publication mechanism
     */
    public static void beanFactoryAndApplicationContext() {

    }

    /**
     * NOTE:
     *   ApplicationContext cascades those calls to all Lifecycle implementations that defined within that context,
     *   and it does this by delegating to a LifecycleProcessor which is DefaultLifecycleProcessor by default.
     *
     *   # Lifecycle beans are not include non-singleton beans.
     *   # Only AbstractApplicationContext.start() can fire Lifecycle beans.
     *   # Both AbstractApplicationContext.refresh() and start() can fire SmartLifecycle beans.
     *   # The SmartLifecycle should turns to not running state before LifecycleProcessor invoke its stop().
     *
     * @see org.springframework.context.Lifecycle only AbstractApplicationContext.start() can fire it
     * @see org.springframework.context.SmartLifecycle both AbstractApplicationContext.refresh() and start() can fire it
     * @see org.springframework.context.Lifecycle#isRunning() a critical state of the Lifecycle, impacts start() and stop()
     * @see org.springframework.context.LifecycleProcessor
     * @see org.springframework.context.support.DefaultLifecycleProcessor core action bearer
     * @see org.springframework.context.support.DefaultLifecycleProcessor#doStop(Map, String, CountDownLatch, Set)
     *
     * @see org.springframework.context.support.AbstractApplicationContext#refresh()
     * @see org.springframework.context.support.AbstractApplicationContext#finishRefresh()
     */
    public static void objectLifecycle() {
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue("title", "ProfitMargin");
        values.addPropertyValue("start", System.currentTimeMillis() + 1000);
        values.addPropertyValue("duration", 5000);
        BeanDefinitionCustomizer customizer = (definition) -> {
            RootBeanDefinition beanDefinition = (RootBeanDefinition) definition;
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanDefinition.setPropertyValues(values);
        };

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(Seminar.class, customizer);
        context.refresh();  // SmartLifecycle.start()
        context.start();    // Lifecycle.start() and SmartLifecycle.start()

        Seminar seminar = context.getBean(Seminar.class);
        System.out.println(seminar);

        try {
            TimeUnit.MILLISECONDS.sleep(1200);  // waiting to start the Seminar and Meeting
        } catch (InterruptedException e) {e.printStackTrace();}
        context.close();
    }

    /**
     * NOTE:
     *   For fine-grained control over auto-startup of a specific bean.
     *   SmartLifecycle needs to follow the default method implementation.
     *
     * @see org.springframework.context.SmartLifecycle#stop(Runnable) callback.run()
     * @see org.springframework.context.SmartLifecycle#getPhase()
     */
    public static void smartLifecycle() {
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue("title", "ProfitMargin");
        values.addPropertyValue("start", System.currentTimeMillis() + 1000);
        values.addPropertyValue("duration", 1000);
        BeanDefinitionCustomizer customizer = (definition) -> {
            RootBeanDefinition beanDefinition = (RootBeanDefinition) definition;
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanDefinition.setPropertyValues(values);
        };

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(Meeting.class, customizer);
        context.refresh();  // SmartLifecycle.start()

        Meeting meeting = context.getBean(Meeting.class);
        System.out.println(meeting);

        try {
            TimeUnit.MILLISECONDS.sleep(1000);  // adjust time to verify Meeting start() or stop()
        } catch (InterruptedException e) {e.printStackTrace();}
        context.close();
    }

    /**
     * @see AbstractAutowireCapableBeanFactory#doCreateBean
     *
     * Instantiation
     *  static block
     *  constructor
     * Populate
     * Initialize
     *  aware
     * BeanPostProcessor before
     *  post construct
     *  after properties
     *  init-method
     * BeanPostProcessor after
     * ... ...
     * Destroy
     */
    static void beanLifecycle() {
        GenericApplicationContext context = new AnnotationConfigApplicationContext(FocusConfiguration.class);
//        context.getBean(CompositeBean.class);
        context.close();
    }

    /**
     * There is no gracefully shutdown ApplicationContext implementation in context module,
     * so we need to implement one first.
     *
     * @see org.springframework.context.support.AbstractApplicationContext#shutdownHook
     */
    public static void shutdownGracefully() {
        GenericApplicationContext context = new GracefulApplicationContext();
        context.registerShutdownHook();     // context.close() will remove a shutdown hook
        context.refresh();       // active context, or it will not invoke doClose() while invoke close()
//        context.close();         // a specific way to close the ApplicationContext, even without register a shutdown hook

        System.exit(1);  // invoke shutdown hook before JVM shutdown
    }

    /**
     * NOTE: By default, event listeners receive events synchronous.
     *
     * @see org.springframework.context.ApplicationEvent
     * @see org.springframework.context.ApplicationListener
     * @see org.springframework.context.event.ApplicationEventMulticaster
     * @see org.springframework.context.event.SimpleApplicationEventMulticaster
     * @see org.springframework.context.support.AbstractApplicationContext#initApplicationEventMulticaster()
     *
     * @see org.iushu.context.ApplicationComponents#applicationListenDetector()
     * @see org.iushu.context.ApplicationComponents#eventListenerMethodProcessor()
     */
    public static void applicationEvent() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(FocusApplicationEventPublisherAware.class);
        context.addApplicationListener(new FocusApplicationListener());  // also add to ApplicationEventMulticaster
        context.refresh();

        Conductor conductor = new Conductor();
        conductor.setName("Derek Amy");
        conductor.start();

        FocusApplicationEventPublisherAware aware = context.getBean(FocusApplicationEventPublisherAware.class);
        aware.publishEvent(conductor);

        conductor.pause();
        aware.publishEvent(conductor);

        conductor.end();
        aware.publishEvent(conductor);
    }

    /**
     * BeanFactoryPostProcessor is defined in bean module but apply actually in context module.
     * (see also XML-based configuration in spring-context.xml)
     *
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
     * @see org.springframework.context.support.AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)
     * @see org.springframework.core.io.support.PropertiesLoaderSupport#loadProperties(Properties)
     * @see org.springframework.beans.factory.config.BeanDefinitionVisitor used by PlaceholderConfigurerSupport
     */
    public static void propertySourceConfigurer() {
        Resource resource = new ClassPathResource("org/iushu/context/jdbc.properties");
        PropertyResourceConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(resource);

        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue("driver", "${jdbc.driver}");
        values.addPropertyValue("url", "${jdbc.url}");
        values.addPropertyValue("username", "${jdbc.user}");
        values.addPropertyValue("password", "${jdbc.password}");
        BeanDefinitionCustomizer customizer = (definition) -> {
            RootBeanDefinition beanDefinition = (RootBeanDefinition) definition;
            beanDefinition.setPropertyValues(values);
        };

        GenericApplicationContext context = new GenericApplicationContext();
        context.addBeanFactoryPostProcessor(configurer);
        context.registerBean(ConnectionMetadata.class, customizer);
        context.refresh();

        ConnectionMetadata metadata = context.getBean(ConnectionMetadata.class);
        System.out.println(metadata);
    }

    public static void checkComponents(AbstractApplicationContext context) {
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
    }

    /**
     * Internal components while configured <context:annotation-config/>
     *
     * @see org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser#parse(Element, ParserContext)
     * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry, Object)
     *
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor supports @Configuration annotation
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
     * @see org.springframework.context.event.EventListenerMethodProcessor supports @EventListener annotation
     * @see org.springframework.context.event.DefaultEventListenerFactory supports @EventListener annotation
     *
     * @see ApplicationComponents#main(String[])  more details in components of ApplicationContext
     */
    public static void contextAnnotationConfig() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("org/iushu/context/spring-context.xml");
        context.refresh();
        checkComponents(context);
    }

    /**
     * Internal components while configured <context:component-scan/>
     * NOTE: <context:component-scan/> implicitly enables the functionality of <context:annotation-config/>
     *
     * BeanPostProcessor
     * @see org.springframework.context.support.ApplicationContextAwareProcessor supports the *Aware interfaces
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor.ImportAwareBeanPostProcessor
     * @see org.springframework.context.support.ApplicationListenerDetector
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
     *
     * BeanFactoryPostProcessor
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor supports @Configuration annotation
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
     * @see org.springframework.context.event.EventListenerMethodProcessor supports @EventListener annotation
     * @see org.springframework.context.event.DefaultEventListenerFactory supports @EventListener annotation
     */
    public static void contextComponentScan() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("org/iushu/context/spring-context.xml");
        context.refresh();
        checkComponents(context);
    }

    /**
     * Spring provides a hierarchical search to query a PropertySource in Environment,
     * the hierarchical sources is configurable by using addFirst() or addLast().
     * For a common StandardServletEnvironment, the full hierarchy is as follows:
     *  1. ServletConfig parameters
     *  2. ServletContext parameters (web.xml context-param entries)
     *  3. JNDI environment variables (java:comp/env/..)
     *  4. JVM System properties (-D command-line properties)
     *  5. JVM System environment (from System.env())
     *
     * @see org.springframework.core.env.PropertySources
     * @see org.springframework.core.env.PropertySource
     * @see org.springframework.context.annotation.PropertySource
     * @see org.iushu.context.annotation.FocusConfiguration
     */
    public static void environment() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("/media/iushu/120bd41f-5ddb-45f2-9233-055fdc3aca07/workplace-idea/components/spring/src/main/java/org/iushu/context/jdbc.properties"));
            PropertiesPropertySource source = new PropertiesPropertySource("jdbc", prop);

            GenericApplicationContext context = new GenericApplicationContext();
            ConfigurableEnvironment environment = context.getEnvironment();
            MutablePropertySources sources = environment.getPropertySources();
            sources.addFirst(source);

            System.out.println(environment.getProperty("jdbc.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * NOTE: Be aware of the resource bundle names
     *
     * @see org.springframework.context.support.AbstractApplicationContext#messageSource
     * @see org.springframework.context.support.AbstractApplicationContext#initMessageSource()
     * @see org.springframework.context.MessageSource
     * @see org.springframework.context.support.ResourceBundleMessageSource
     * @see org.springframework.context.support.AbstractResourceBasedMessageSource#setBasenames(String...)
     *
     * @see java.util.Locale
     * @see java.util.ResourceBundle
     * @see java.util.ResourceBundle.Control
     */
    public static void messageSource() {
        GenericApplicationContext context = new GenericApplicationContext();
        RootBeanDefinition definition = new RootBeanDefinition(ResourceBundleMessageSource.class);
        definition.getPropertyValues().add("basenames", "org.iushu.context.globalization");
        context.registerBeanDefinition(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, definition);
        context.refresh();

//        String message = context.getMessage("login.password.wrong", null, Locale.CHINA);
        String message = context.getMessage("login.password.wrong", null, Locale.US);
        System.out.println(message);
    }

    /**
     * By default, event listeners receive events synchronous,
     * use @Async to transform the listener become asynchronous.
     * NOTE: Asynchronous methods cannot propagate an Exception to the caller.
     * NOTE: Asynchronous event methods cannot publish a subsequent event by returning an event.
     *
     * @see org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor#invoke(MethodInvocation)
     * @see org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor supports @Async method (AOP.MethodInterceptor)
     * @see org.springframework.context.event.SimpleApplicationEventMulticaster#multicastEvent(ApplicationEvent, ResolvableType)
     *
     * @see FocusAsyncApplicationListener
     * @see org.iushu.context.ApplicationComponents#eventListenerMethodProcessor()
     */
    public static void asyncHandleEvent() {
//        String projectLocation = System.getProperty("user.dir");
//        System.setProperty("cglib.debugLocation", projectLocation + "/src/main/java/org/iushu/aop/proxy/classes/");

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(DefaultEventListenerFactory.class);
        context.registerBean(EventListenerMethodProcessor.class);
        context.registerBean(AsyncAnnotationBeanPostProcessor.class);
//        context.registerBean(SimpleAsyncTaskExecutor.class);
        context.registerBean(FocusAsyncApplicationListener.class);
        context.refresh();

        Conductor conductor = new Conductor();
        conductor.setName("Alexandre Joe");
        conductor.start();
        context.publishEvent(new ConductorEvent(conductor));
    }

    /**
     * @see org.springframework.core.metrics.ApplicationStartup
     * @see org.springframework.core.metrics.DefaultApplicationStartup
     * @see org.springframework.context.ApplicationStartupAware
     * @see AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
     */
    public static void applicationStartup() {

    }

    public static void main(String[] args) {
//        objectLifecycle();
//        smartLifecycle();
        beanLifecycle();
//        shutdownGracefully();
//        applicationEvent();
//        propertySourceConfigurer();
//        contextAnnotationConfig();
//        contextComponentScan();
//        environment();
//        messageSource();
//        asyncHandleEvent();
//        applicationStartup();
    }

}
