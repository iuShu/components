package org.iushu.context;

import org.iushu.context.annotation.FocusConfiguration;
import org.iushu.context.annotation.beans.Pet;
import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.iushu.context.beans.Microphone;
import org.iushu.context.beans.ScreenRemote;
import org.iushu.context.components.FocusApplicationListener;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.event.DefaultEventListenerFactory;
import org.springframework.context.event.EventListenerMethodProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Set;

import static org.iushu.context.Application.checkComponents;

/**
 * Internal components while configured <context:annotation-config/>.
 *
 * @author iuShu
 * @since 2/19/21
 */
public class ApplicationComponents {

    /**
     * Components for support annotation @Configuration
     *
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
     * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitions(Set)
     * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod(org.springframework.context.annotation.BeanMethod) supports @Bean in @Configuration
     * @see org.iushu.context.ApplicationAnnotation#configuration() more details
     */
    private static void configurationClassPostProcessor() {
        // The Configuration class will be enhanced by Cglib, add this attribute to check the source code of the enhanced class.
        String projectLocation = System.getProperty("user.dir");
        System.setProperty("cglib.debugLocation", projectLocation + "/src/main/java/org/iushu/aop/proxy/classes/");

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(FocusConfiguration.class);
        context.refresh();
        checkComponents(context);

        Pet pet = context.getBean(Pet.class);
        System.out.println(pet);
    }

    /**
     * TODO How CommonAnnotationBeanPostProcessor integrating the JNDI module in Spring
     *
     * @see javax.annotation.Resource
     * @see javax.annotation.PostConstruct
     * @see javax.annotation.PreDestroy
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor supports @Resource @PreDestroy @PostConstruct
     * @see org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor the superclass that actually supports the annotations
     *
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor supports @Autowired @Value
     *
     * @see org.springframework.beans.factory.annotation.Autowired byType then byName
     * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
     * @see org.springframework.beans.factory.annotation.InjectionMetadata#inject(Object, String, PropertyValues)
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String)
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)
     * @see org.springframework.beans.factory.support.AutowireCandidateResolver#getSuggestedValue(DependencyDescriptor)
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveMultipleBeans(DependencyDescriptor, String, Set, TypeConverter)
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)
     *
     * NOTE: Spring would changes to byType mode and invoke BeanFactory#resolveDependency(like @Autowired did) if failed in byName mode.
     * @see javax.annotation.Resource byName then byType
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
     * @see org.springframework.beans.factory.annotation.InjectionMetadata#inject(Object, String, PropertyValues)
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.ResourceElement#inject(Object, String, PropertyValues)
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.ResourceElement#getResourceToInject(Object, String)
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor#getResource(CommonAnnotationBeanPostProcessor.LookupElement, String)
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor#autowireResource(BeanFactory, CommonAnnotationBeanPostProcessor.LookupElement, String)
     *
     * Components for autowire candidate
     * @see org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement supports @Autowired
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredMethodElement supports @Autowired
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.ResourceElement supports @Resource
     *
     */
    private static void commonAnnotationBeanPostProcessor() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("org/iushu/context/spring-context.xml");
//        GenericApplicationContext context = new GenericApplicationContext();
//        context.registerBean(CommonAnnotationBeanPostProcessor.class);
//        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
//        context.registerBean(Microphone.class);
//        context.registerBean(ScreenRemote.class);
//        context.refresh();

        // @Autowired vs @Resource
        Conductor conductor = context.getBean(Conductor.class);
        System.out.println(conductor);

        context.close();
    }

    /**
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor responsible for processing @Autowired & @Value
     * @see org.iushu.expression.Application#beanDefinition()
     */
    private static void autowiredAnnotationBeanPostProcessor() {

    }

    /**
     * @see org.springframework.context.support.ApplicationListenerDetector
     * @see org.springframework.context.support.AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)
     */
    public static void applicationListenDetector() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(FocusApplicationListener.class);
        context.registerBean(Conductor.class);
        context.refresh();

        Conductor conductor = context.getBean(Conductor.class);
        conductor.setName("Mike");
        conductor.start();
        context.publishEvent(new ConductorEvent(conductor));
        checkComponents(context);
    }

    /**
     * Components for support annotation @EventListener
     *
     * @see org.springframework.context.event.EventListener
     * @see org.springframework.context.event.EventListenerMethodProcessor supports @EventListener annotation
     * @see org.springframework.context.event.DefaultEventListenerFactory create EventListener from method for EventListenerMethodProcessor
     * @see org.springframework.context.event.ApplicationListenerMethodAdapter wrap a method to be an ApplicationListener
     * @see org.springframework.context.event.EventExpressionEvaluator
     */
    private static void eventListenerMethodProcessor() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(DefaultEventListenerFactory.class);
        context.registerBean(EventListenerMethodProcessor.class);
        context.registerBean(FocusApplicationListener.class);
        context.refresh();  // will publish a built-in ContextRefreshedEvent as finishRefresh()

        Conductor conductor = new Conductor();
        conductor.setName("Reo Jackson");
        conductor.start();
        context.publishEvent(new ConductorEvent(conductor));
    }

    public static void main(String[] args) {
        configurationClassPostProcessor();
//        commonAnnotationBeanPostProcessor();
//        autowiredAnnotationBeanPostProcessor();
//        applicationListenDetector();
//        eventListenerMethodProcessor();
    }

}
