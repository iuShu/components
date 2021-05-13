package org.iushu.dubbo.spring;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.DubboConfigAliasPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.config.DubboConfigDefaultPropertyValueBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.config.DubboConfigEarlyInitializationPostProcessor;
import org.apache.dubbo.config.spring.context.DubboApplicationListenerRegistrar;
import org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener;
import org.apache.dubbo.config.spring.context.DubboLifecycleComponentApplicationListener;
import org.apache.dubbo.config.spring.schema.DubboBeanDefinitionParser;
import org.apache.dubbo.config.spring.schema.DubboNamespaceHandler;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import org.apache.dubbo.registry.client.migration.MigrationInvoker;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.interceptor.ClusterInterceptor;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.wrapper.MockClusterWrapper;
import org.apache.dubbo.rpc.proxy.InvokerInvocationHandler;
import org.iushu.dubbo.Utils;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.component.ApplicantNotify;
import org.iushu.dubbo.component.DefaultApplicantNotify;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;
import org.iushu.dubbo.spring.service.ItemService;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
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
     * @see DubboNamespaceHandler#init()
     * @see DubboNamespaceHandler#parse(Element, ParserContext)
     * @see DubboNamespaceHandler#registerAnnotationConfigProcessors(BeanDefinitionRegistry)
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     * @see DubboBeanDefinitionParser#parse
     *
     * ApplicationListener
     *
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     * @see DubboApplicationListenerRegistrar#addApplicationListeners(ConfigurableApplicationContext)
     * @see DubboBootstrapApplicationListener [CORE]
     * @see DubboLifecycleComponentApplicationListener
     *
     * BeanPostProcessor x 4
     *
     * @see DubboBeanUtils#registerCommonBeans(BeanDefinitionRegistry)
     * @see DubboConfigEarlyInitializationPostProcessor add ConfigManager
     * @see DubboConfigAliasPostProcessor register configs' alias
     * @see DubboConfigDefaultPropertyValueBeanPostProcessor set bean's name as default value
     * @see ReferenceAnnotationBeanPostProcessor inject ReferenceObject for consuming
     */
    static void components() {

    }

    /**
     * Load NamespaceHandler from /META-INF/spring.handlers to parse configuration
     * @see XmlBeanDefinitionReader#createReaderContext(Resource)
     * @see XmlReaderContext#namespaceHandlerResolver
     * @see XmlBeanDefinitionReader#getNamespaceHandlerResolver() loading from /META-INF/spring.handlers
     * @see DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions(Element)
     * @see DefaultBeanDefinitionDocumentReader#parseBeanDefinitions(Element, BeanDefinitionParserDelegate)
     * @see BeanDefinitionParserDelegate#parseCustomElement(Element, BeanDefinition)
     * @see DubboNamespaceHandler#parse(Element, ParserContext)
     * @see DubboBeanDefinitionParser#beanClass set bean class in BeanDefinition (like ReferenceBean)
     *
     * Register DubboBoostrap into BeanFactory
     * @see DubboBeanUtils#registerCommonBeans register DubboBootstrapApplicationListener
     * @see DubboApplicationListenerRegistrar#createDubboBootstrapApplicationListener
     * @see DubboBootstrap#getInstance() create DubboBoostrap
     *
     * Preinstantiate consumer service in BeanFactory
     * @see DefaultListableBeanFactory#preInstantiateSingletons()
     * @see ReferenceBean service BeanDefinition class is ReferenceBean(FactoryBean)
     * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
     * @see InjectionMetadata#inject(Object, String, PropertyValues)
     * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject
     * @see DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String)
     * @see FactoryBean#getObject()
     * @see ReferenceBean#get() access registry center and connect to provider server
     *
     * DubboBootstrap startup
     * @see AbstractApplicationContext#refresh()
     * @see AbstractApplicationContext#finishRefresh()
     * @see AbstractApplicationContext#publishEvent(ApplicationEvent)
     * @see DubboBootstrapApplicationListener#onApplicationContextEvent(ApplicationContextEvent)
     * @see DubboBootstrapApplicationListener#onContextRefreshedEvent(ContextRefreshedEvent)
     * @see DubboBootstrap#start() start Dubbo
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
     * TODO how Spring autowired the ReferenceBean
     *
     * @see ItemService#itemWarehouse
     * @see #provider() start provider first
     */
    static void autowiredCase() {
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
     * NOTE: The callback services are also proxied to a ReferenceBean like ItemWarehouse
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

    /**
     * tolerate provider fail and mock the provider invoker
     * @see MockClusterInvoker#doMockInvoke(Invocation, RpcException)
     * @see MockClusterWrapper
     *
     * @see #provider() start provider first
     */
    static void failToleranceLocalCase() {
        String configPath = "spring-dubbo-consumer.xml";
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(configPath);

        ItemWarehouse itemWarehouse = (ItemWarehouse) context.getBean("applyItemWarehouse");
        sleep(10000);   // close provider while consumer in sleeping
        Item item = itemWarehouse.getItem(821232);  // failover to FailToleranceItemWarehouse
        System.out.println(item);
    }

    /**
     * There are something preparatory work to do before export, like loading redis cache.
     * see <dubbo:service delay="5000"/> millisecond
     * @see org.apache.dubbo.config.ServiceConfig#delay
     */
    static void delayExportServiceCase() {

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
        autowiredCase();
//        callbackServiceCase();
//        failToleranceLocalCase();
//        dubboInSpringCase();
    }

}
