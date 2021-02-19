package org.iushu.ioc;

import org.iushu.ioc.beans.*;
import org.iushu.ioc.components.FocusPropertyEditorSupport;
import org.iushu.ioc.components.FocusPropertyEditoryRegistrar;
import org.springframework.beans.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;


/**
 * @author iuShu
 * @since 12/31/20
 */
public class Application {

    static BeanFactory getBeanFactory() {
        Resource configResource = new ClassPathResource("org/iushu/ioc/spring-ioc.xml");
        return new XmlBeanFactory(configResource);
    }

    public static void populateBean() {
        BeanFactory beanFactory = getBeanFactory();
        Manufacturer manufacturer = beanFactory.getBean(Manufacturer.class);
        System.out.println(manufacturer);
    }

    public static void interfaces() {
        BeanFactory beanFactory = getBeanFactory();
        BeanPostProcessor processor = beanFactory.getBean(BeanPostProcessor.class);  // addBeanPostProcessor
        System.out.println(processor.getClass().getName());

        Manufacturer mfu = beanFactory.getBean("mft", Manufacturer.class);
        Manufacturer manufacturer = beanFactory.getBean("manufacturer", Manufacturer.class);
        System.out.println(mfu);
        System.out.println(manufacturer);
        System.err.println(manufacturer == mfu);
    }

    public static void factoryBean() {
        BeanFactory beanFactory = getBeanFactory();
        beanFactory.getBean(BeanPostProcessor.class);  // addBeanPostProcessor

        // declared register a bean into BeanFactory
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        registry.registerBeanDefinition("staffFactory", new RootBeanDefinition(StaffFactory.class));

        // exception: BeanFactory is unavailable to determine which one should be returned
//        Staff staff = beanFactory.getBean(Staff.class);

        // work as a normal Bean
        FactoryBean staffFactory = beanFactory.getBean(FactoryBean.class);
        System.out.println(staffFactory.getObjectType());

        // avoid conflict with Staff bean that creates from FactoryBean
        registry.removeBeanDefinition("manager");
        // getBean from a FactoryBean
        Staff staff = beanFactory.getBean(Staff.class);
        System.out.println(staff);
    }

    /**
     * Explicitly to force one or more beans to be initialized before some beans.
     * (e.g. Static method)
     *
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)
     */
    public static void dependsOn() {
        BeanFactory beanFactory = getBeanFactory();
        Manager manager = beanFactory.getBean(Manager.class);
        System.out.println(manager);
        manager.punchIn();
    }

    /**
     * The lazy-init feature actually use at context module
     *
     * @see org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
     * @see org.springframework.beans.factory.config.ConfigurableListableBeanFactory#preInstantiateSingletons()
     */
    public static void lazyInitBean() {
        RootBeanDefinition corporationDefinition = new RootBeanDefinition(Deliver.class);
        RootBeanDefinition deliverDefinition = new RootBeanDefinition(Deliver.class);
        deliverDefinition.setLazyInit(true);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("corporation", corporationDefinition);
        beanFactory.registerBeanDefinition("deliver", deliverDefinition);

        System.out.println(beanFactory.containsSingleton("corporation"));   // false
        System.out.println(beanFactory.containsSingleton("deliver"));       // false
        beanFactory.preInstantiateSingletons();
        System.out.println(beanFactory.containsSingleton("corporation"));   // true
        System.out.println(beanFactory.containsSingleton("deliver"));       // false
    }

    /**
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#createBean(String, RootBeanDefinition, Object[])
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#instantiateBean(String, RootBeanDefinition)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#instantiationStrategy responisble for instantiation
     * @see org.springframework.beans.factory.support.SimpleInstantiationStrategy#instantiate(RootBeanDefinition, String, BeanFactory)
     * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.LookupOverrideMethodInterceptor
     */
    public static void abstractMethodInjection() {
        BeanFactory beanFactory = getBeanFactory();
        Warehouse warehouse = beanFactory.getBean(Warehouse.class);
        Manager manager = warehouse.createManager();
        System.out.println(manager);
    }

    /**
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#createBean(String, RootBeanDefinition, Object[])
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#instantiateBean(String, RootBeanDefinition)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#instantiationStrategy responisble for instantiation
     * @see org.springframework.beans.factory.support.SimpleInstantiationStrategy#instantiate(RootBeanDefinition, String, BeanFactory)
     * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.ReplaceOverrideMethodInterceptor
     */
    public static void arbitraryMethodReplacer() {
//        System.setProperty("cglib.debugLocation", System.getProperty("user.dir") + "/src/main/java/org/iushu/aop/proxy/classes/");
        BeanFactory beanFactory = getBeanFactory();
        Warehouse warehouse = beanFactory.getBean(Warehouse.class);
        Manager manager = beanFactory.getBean(Manager.class);
        warehouse.fire(manager);
        System.out.println(manager);
    }

    /**
     * NOTE:
     *   It is recommended to use @PostConstruct and @PreDestroy to receive Lifecycle callbacks,
     *   due to unnecessarily couples the code to Spring, like implementing InitializingBean.
     *
     *   PostConstruct is equals to init-method in XML-based
     *   PreDestroy is equals to destroy-method in XML-based
     *
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#invokeInitMethods(String, Object, RootBeanDefinition)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#registerDisposableBeanIfNecessary(String, Object, RootBeanDefinition)
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter
     *
     * @see org.springframework.beans.factory.config.SingletonBeanRegistry
     * @see org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#destroySingletons()
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter#destroy()
     */
    public static void beanLifecycle() {
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue("name", "Boris Johnson");

        RootBeanDefinition definition = new RootBeanDefinition(Staff.class);
        definition.setInitMethodName("init");
        definition.setDestroyMethodName("destroy");
        definition.setPropertyValues(values);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("staff", definition);

        Staff staff = beanFactory.getBean(Staff.class);
        System.out.println(staff);

        System.out.println(beanFactory.containsBeanDefinition("staff"));
        System.out.println(beanFactory.containsBean("staff"));
        beanFactory.destroySingletons();                         // remove singletons but still able to create a new singleton bean
        beanFactory.removeBeanDefinition("staff");     // remove bean completely
        System.out.println(beanFactory.containsBean("staff"));
        System.out.println(beanFactory.containsBeanDefinition("staff"));
    }

    /**
     * @see org.iushu.context.Application#objectLifecycle()
     */
    public static void objectLifecycle() {

    }

    /**
     * NOTE: Use in org.springframework.context.validation module, with DataBinder.
     *
     * @see org.springframework.beans.BeanWrapper
     * @see org.springframework.beans.BeanWrapperImpl
     * @see org.springframework.beans.PropertyEditorRegistry
     */
    public static void beanWrapper() {
        Manufacturer manufacturer = new Manufacturer();
        BeanWrapper beanWrapper = new BeanWrapperImpl(manufacturer);

        beanWrapper.setPropertyValue("mid", "8A576I6XT1");

        PropertyValue propertyValue = new PropertyValue("name", "Bingo");
        beanWrapper.setPropertyValue(propertyValue);

        Manager manager = new Manager();
        beanWrapper.setPropertyValue("manager", manager);
        beanWrapper.setPropertyValue("manager.name", "Huff");  // Nested properties

        int level = (int) beanWrapper.getPropertyValue("manager.level");  // Nested properties

        System.out.println(level);
        System.out.println(manufacturer);
        System.out.println(beanWrapper.getWrappedInstance());
    }

    /**
     * Register PropertyEditor by CustomEditorConfigurer(BeanFactoryPostProcessor)
     *
     * @see java.beans.PropertyEditor JavaBeans property editor (most of built-in editors are registered by BeanWrapperImpl)
     * @see java.beans.PropertyEditorSupport general class to use
     * @see org.springframework.beans.PropertyEditorRegistry for register the PropertyEditor, implemented by BeanWrapper
     * @see org.springframework.beans.PropertyEditorRegistrar for customed register PropertyEditor with a PropertyEditorRegistry
     *
     * @see org.springframework.beans.factory.config.CustomEditorConfigurer
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#registerCustomEditor(Class, Class)
     * @see org.springframework.beans.BeanWrapperImpl#convertForProperty(Object, String)
     */
    public static void propertyEditorConfigurer() {
        Map<Class<?>, Class<? extends PropertyEditor>> editors = new HashMap<>();
        editors.put(Manager.class, FocusPropertyEditorSupport.class);

        CustomEditorConfigurer editorConfigurer = new CustomEditorConfigurer();
        editorConfigurer.setCustomEditors(editors);

        // manually apply BeanFactoryPostProcessor to a BeanFactory
        BeanFactory beanFactory = getBeanFactory();
        editorConfigurer.postProcessBeanFactory((ConfigurableListableBeanFactory) beanFactory);

        Corporation corporation = beanFactory.getBean(Corporation.class);
        System.out.println(corporation);
    }

    /**
     * Register PropertyEditor by built-in component in ConfigurableListableBeanFactory
     *
     * @see org.springframework.beans.PropertyEditorRegistrar
     * @see org.springframework.beans.PropertyEditorRegistry actally registering action
     *
     * Implementaion in ApplicationContext
     * @see org.springframework.context.support.AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
     */
    public static void propertyEditorRegistrar() {
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) getBeanFactory();

        PropertyEditorRegistrar editorRegistrar = new FocusPropertyEditoryRegistrar();
        beanFactory.addPropertyEditorRegistrar(editorRegistrar);

        Corporation corporation = beanFactory.getBean(Corporation.class);
        System.out.println(corporation);
    }

    public static void main(String[] args) {
//        populateBean();
//        interfaces();
//        factoryBean();
//        dependsOn();
        lazyInitBean();
//        abstractMethodInjection();
//        arbitraryMethodReplacer();
//        beanLifecycle();
//        beanWrapper();
//        propertyEditorConfigurer();
//        propertyEditorRegistrar();
    }

}
