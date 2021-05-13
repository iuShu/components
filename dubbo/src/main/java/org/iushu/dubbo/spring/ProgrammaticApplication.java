package org.iushu.dubbo.spring;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.spring.beans.factory.annotation.DubboConfigAliasPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.config.DubboConfigDefaultPropertyValueBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.config.DubboConfigEarlyInitializationPostProcessor;
import org.apache.dubbo.config.spring.context.DubboApplicationListenerRegistrar;
import org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener;
import org.apache.dubbo.config.spring.context.DubboLifecycleComponentApplicationListener;
import org.apache.dubbo.config.spring.schema.DubboNamespaceHandler;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import org.iushu.dubbo.Utils;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;
import org.iushu.dubbo.component.DefaultApplicantNotify;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;
import org.iushu.dubbo.spring.service.ItemService;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static org.iushu.dubbo.Utils.sleep;

/**
 * @author iuShu
 * @since 5/11/21
 */
public class ProgrammaticApplication {

    /**
     * Configuration
     *
     *  see dubbo.jar/META-INF/spring.handlers(schemas)
     * @see DubboNamespaceHandler#parse(Element, ParserContext)
     * @see DubboNamespaceHandler#registerAnnotationConfigProcessors(BeanDefinitionRegistry)
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     *
     * ApplicationListener
     *
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     * @see DubboApplicationListenerRegistrar#addApplicationListeners(ConfigurableApplicationContext)
     * @see DubboBootstrapApplicationListener [CORE]
     * @see DubboLifecycleComponentApplicationListener
     *
     * BeanPostProcessor
     *
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     * @see DubboConfigEarlyInitializationPostProcessor add ConfigManager
     * @see DubboConfigEarlyInitializationPostProcessor#processBeforeInitialization
     * @see DubboConfigAliasPostProcessor register configs' alias
     * @see DubboConfigAliasPostProcessor#postProcessAfterInitialization(Object, String)
     * @see DubboConfigDefaultPropertyValueBeanPostProcessor set bean's name as default value
     * @see DubboConfigDefaultPropertyValueBeanPostProcessor#setBeanNameAsDefaultValue(Object, String, String)
     * @see ReferenceAnnotationBeanPostProcessor inject ReferenceObject for consuming
     * @see ReferenceAnnotationBeanPostProcessor#getInjectedObject
     */
    static void components() {

    }

    /**
     * @see AbstractApplicationContext#refresh()
     * @see AbstractApplicationContext#finishRefresh()
     * @see AbstractApplicationContext#publishEvent(ApplicationEvent)
     * @see DubboBootstrapApplicationListener#onApplicationContextEvent(ApplicationContextEvent)
     * @see DubboBootstrapApplicationListener#onContextRefreshedEvent(ContextRefreshedEvent)
     * @see DubboBootstrap#start()
     */
    static void startup() {

    }

    static void provider() {
        String configPath = "spring-dubbo-provider.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configPath);
//        checkComponents(context);
        DubboBootstrap.getInstance().await();   // wait for consuming
    }

    static void consumer() {
        String configPath = "spring-dubbo-consumer.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        ItemWarehouse itemWarehouse = (ItemWarehouse) context.getBean("itemWarehouse");
        Item item = itemWarehouse.getItem(291831);
        System.out.println(item);

        checkComponents(context);
        context.close();
    }

    /**
     * @see ItemService#itemWarehouse
     */
    static void autowiredCase() {
        new Thread(ProgrammaticApplication::provider, "provider").start();

        String configPath = "spring-dubbo-consumer.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        ItemService itemService= context.getBean(ItemService.class);
        Item item = itemService.getItem();
        System.out.println(item);

        checkComponents(context);
        context.close();
    }

    /**
     * Callback service application case
     *  configure callback argument at provider side
     *  see spring-dubbo-provider.xml (argument.index start from 0)
     *
     * @see #provider() start provider first
     */
    static void callbackServiceCase() {
        String configPath = "spring-dubbo-consumer.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        ItemWarehouse itemWarehouse = (ItemWarehouse) context.getBean("applyItemWarehouse");
        List<Item> items = new ArrayList<>();
        items.add(CenterItemWarehouse.createItem(828132));
        items.add(CenterItemWarehouse.createItem(207472));
        ApplicantNotify notify = new DefaultApplicantNotify();  // callback service
        itemWarehouse.applyItems(items, notify);
    }

    static void dubboInSpringCase() {
        new Thread(ProgrammaticApplication::provider, "provider").start();
        sleep(3000);
        new Thread(ProgrammaticApplication::consumer, "consumer").start();
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
//        provider();
//        consumer();
//        autowiredCase();
        callbackServiceCase();
//        dubboInSpringCase();
    }

}
