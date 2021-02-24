package org.iushu.context;

import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConnectionMetadata;
import org.iushu.context.beans.Meeting;
import org.iushu.context.beans.Seminar;
import org.iushu.context.components.FocusApplicationContextAware;
import org.iushu.context.components.FocusApplicationEventPublisherAware;
import org.iushu.context.components.FocusApplicationListener;
import org.iushu.context.components.GracefulApplicationContext;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.springframework.context.support.AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME;

/**
 * @author iuShu
 * @since 1/22/21
 */
public class Application {

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
     * @see org.springframework.context.ApplicationEvent
     * @see org.springframework.context.ApplicationListener
     * @see org.springframework.context.event.ApplicationEventMulticaster
     * @see org.springframework.context.event.SimpleApplicationEventMulticaster
     * @see org.springframework.context.support.AbstractApplicationContext#initApplicationEventMulticaster()
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

    public static void main(String[] args) {
//        objectLifecycle();
//        smartLifecycle();
//        shutdownGracefully();
//        applicationEvent();
//        propertySourceConfigurer();
//        contextAnnotationConfig();
        contextComponentScan();
    }

}
