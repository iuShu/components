package org.iushu.ioc;

import org.iushu.ioc.beans.*;
import org.iushu.ioc.components.FocusPropertyEditorSupport;
import org.iushu.ioc.components.FocusPropertyEditoryRegistrar;
import org.springframework.beans.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author iuShu
 * @since 12/31/20
 */
public class Application {

    static BeanFactory getBeanFactory() {
        Resource configResource = new ClassPathResource("org/iushu/ioc/spring-ioc.xml");
        return new XmlBeanFactory(configResource);
    }

    /**
     * NOTE: Spring can automatically search beans to autowire while configured autowire attribute.
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
     */
    public static void populateBean() {
        BeanFactory beanFactory = getBeanFactory();
        Manufacturer manufacturer = beanFactory.getBean(Manufacturer.class);
        System.out.println(manufacturer);
    }

    /**
     * see configuration also
     */
    public static void cascadePopulate() {
        BeanFactory beanFactory = getBeanFactory();
        Packet packet = beanFactory.getBean(Packet.class);
        System.out.println(packet);
    }

    /**
     * Autowired Candidate and Qualifier in IOC
     *
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor supports @Autowired
     * @see org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver supports @Qualifier
     * @see org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver supports @Qualifier and more in context module
     *
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)
     * @see org.springframework.beans.factory.support.AutowireCandidateResolver#isAutowireCandidate(BeanDefinitionHolder, DependencyDescriptor)
     *
     * register AutowireCandidateResolver in ApplicationContext supports @Qualifier
     * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry, Object)
     */
    public static void autowireProperty() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // supports @Autowired
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // supports @Qualifier
        QualifierAnnotationAutowireCandidateResolver candidateResolver = new QualifierAnnotationAutowireCandidateResolver();
        candidateResolver.setBeanFactory(beanFactory);
        beanFactory.setAutowireCandidateResolver(candidateResolver);

//        RootBeanDefinition packetDef = new RootBeanDefinition(Packet.class);
        beanFactory.registerBeanDefinition("packet", new RootBeanDefinition(Packet.class));

        RootBeanDefinition leoDef = new RootBeanDefinition(Deliver.class);
        MutablePropertyValues values = leoDef.getPropertyValues();
        values.addPropertyValue("name", "Leo Roan");
        beanFactory.registerBeanDefinition("leo", leoDef);

        RootBeanDefinition ivyDef = new RootBeanDefinition(Deliver.class);
        values = ivyDef.getPropertyValues();
        values.addPropertyValue("name", "Ivy Rina");
        beanFactory.registerBeanDefinition("ivy", ivyDef);

        Packet packet = beanFactory.getBean(Packet.class);
        System.out.println(packet);
    }

    /**
     * FactoryBean: inject the bean created from FactoryBean.
     * ObjectFactory: inject a ObjectFactory, getting target bean from BeanFactory while it getting invoke. (lazy inject)
     * ObjectProvider: inject a ObjectProvider, getting target bean from BeanFactory while it getting invoke. (lazy inject)
     *
     * @see org.springframework.beans.factory.FactoryBean
     * @see DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)
     * @see DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)
     *
     * @see org.springframework.beans.factory.ObjectFactory core conception
     * @see org.springframework.beans.factory.ObjectProvider the variant of ObjectFactory, providing stream and more functionalities
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory.DependencyObjectProvider supports ObjectFactory, core component
     *
     * @see javax.inject.Provider standard equivalents to ObjectFactory
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory.Jsr330Factory.Jsr330Provider an implementation fo DependencyObjectProvider
     * @see DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)
     */
    public static void autowireFactoryBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // supports @Autowired and @Inject
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        beanFactory.registerBeanDefinition("factory", new RootBeanDefinition(StaffFactory.class));
        beanFactory.registerBeanDefinition("iceCream", new RootBeanDefinition(Grocery.IceCream.class));
        beanFactory.registerBeanDefinition("coffee", new RootBeanDefinition(Grocery.Coffee.class));
        beanFactory.registerBeanDefinition("grocery", new RootBeanDefinition(Grocery.class));

        Grocery grocery = beanFactory.getBean(Grocery.class);
        System.out.println(grocery);

        Grocery.IceCream iceCream = grocery.iceCream();
        System.out.println(iceCream);

        Grocery.Coffee coffee = grocery.coffee();
        System.out.println(coffee);
    }

    /**
     * NOTE: depended on javax.inject module
     *
     * @see javax.inject.Inject
     * @see javax.inject.Named
     * @see javax.inject.Qualifier
     * @see QualifierAnnotationAutowireCandidateResolver supports javax.inject.Qualifier and javax.inject.Named
     *
     * @see AutowiredAnnotationBeanPostProcessor#AutowiredAnnotationBeanPostProcessor() supports javax.inject.Inject
     * @see QualifierAnnotationAutowireCandidateResolver#QualifierAnnotationAutowireCandidateResolver() supports javax.inject.Qualifier annotation
     *
     * @see DefaultListableBeanFactory#doResolveDependency
     * @see DefaultListableBeanFactory#findAutowireCandidates
     *
     * MORE: javax.inject.Named and javax.annotation.ManagedBean both are equivalents to @Component
     * @see org.iushu.context.ApplicationAnnotation#jsr330()
     */
    public static void jsr330() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // supports javax.inject.Inject
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // supports javax.inject.Name
        QualifierAnnotationAutowireCandidateResolver candidateResolver = new QualifierAnnotationAutowireCandidateResolver();
        candidateResolver.setBeanFactory(beanFactory);
        beanFactory.setAutowireCandidateResolver(candidateResolver);

        beanFactory.registerBeanDefinition("jack", new RootBeanDefinition(Staff.class));
        beanFactory.registerBeanDefinition("staff", new RootBeanDefinition(Staff.class));
        beanFactory.registerBeanDefinition("toy", new RootBeanDefinition(Toy.class));

        Toy toy = beanFactory.getBean(Toy.class);
        System.out.println(toy);
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
     * Note: BeanFactory will create bean in getBean(), do not create in loading configuration
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
//        cascadePopulate();
//        autowireProperty();
        autowireFactoryBean();
//        jsr330();
//        interfaces();
//        factoryBean();
//        dependsOn();
//        lazyInitBean();
//        abstractMethodInjection();
//        arbitraryMethodReplacer();
//        beanLifecycle();
//        beanWrapper();
//        propertyEditorConfigurer();
//        propertyEditorRegistrar();
    }

}
