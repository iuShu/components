package org.iushu.context;

import org.iushu.context.annotation.FocusConfiguration;
import org.iushu.context.annotation.beans.Animal;
import org.iushu.context.annotation.beans.Egg;
import org.iushu.context.annotation.beans.Pet;
import org.iushu.context.annotation.beans.Poultry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

import static org.iushu.context.Application.checkComponents;

/**
 * @author iuShu
 * @since 2/22/21
 */
public class ApplicationAnnotation {

    /**
     * @see org.springframework.context.annotation.Configuration
     * @see org.springframework.context.annotation.ComponentScan
     * @see org.springframework.context.annotation.Bean
     *
     * @see org.springframework.context.annotation.ConfigurationClassPostProcessor supports @Configuration @Bean @Import
     * @see org.springframework.context.annotation.ConfigurationClass wrap a @Configuration class
     *
     * @see org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass supports the Configuration class
     * @see org.springframework.context.annotation.ComponentScanAnnotationParser#parse(AnnotationAttributes, String) supports @ComponentScan
     * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod supports @Bean
     * @see org.springframework.context.annotation.BeanMethod the class that Spring supports @Bean annotation
     *
     * Bean(initMethod="..", destroyMethod="..")
     * @see org.springframework.context.annotation.Bean#initMethod()
     * @see org.springframework.context.annotation.Bean#destroyMethod() automatically find destroy method named "close" and "shutdown" by default
     * @see org.iushu.context.annotation.beans.Poultry#shutdown()
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#registerDisposableBeanIfNecessary
     * @see org.springframework.beans.factory.support.DisposableBeanAdapter#hasDestroyMethod(Object, RootBeanDefinition)
     *
     * BeanPostProcessor and BeanFactoryPostProcessor should configuring in a static method to
     * avoid impacts to other built-in processors.
     *  Like: @Autowired and @Value will failure in AutowiredAnnotationBeanPostProcessor
     * @see FocusConfiguration#postProcessor()
     */
    public static void configuration() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(FocusConfiguration.class);
        context.refresh();

        Egg egg = context.getBean(Egg.class);
        Animal animal = egg.layEgg();
        System.out.println(animal);

        context.close();
    }

    /**
     * Improve the startup performance of large applications by creating a static list
     * of candidates at compilation time.
     * NOTE: depended module 'spring-context-indexer' (see pom.xml)
     *
     * @see org.springframework.stereotype.Indexed
     * @see org.springframework.context.index.CandidateComponentsIndexLoader#doLoadIndex(ClassLoader) load indexer from spring.components
     * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#findCandidateComponents(String)
     * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#addCandidateComponentsFromIndex(CandidateComponentsIndex, String)
     *
     * @see org.springframework.context.index.processor.MetadataStore create Indexed
     * @see javax.annotation.processing.Processor more complex component
     */
    public static void indexed() {
        configuration();
    }

    /**
     * @see javax.inject.Named
     */
    public static void jsr330() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(FocusConfiguration.class);
        context.refresh();

        Pet pet = (Pet) context.getBean("pet");
        System.out.println(pet);
    }

    /**
     * NOTE: @Configuration classes are meta-annotated with @Component
     *
     * @see org.springframework.context.annotation.Configuration
     * @see AnnotationConfigApplicationContext#register(Class[]) register components (refresh after register)
     * @see AnnotationConfigApplicationContext#scan(String...) scan target packages
     */
    public static void annotationContext() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(FocusConfiguration.class);  // with @Configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(Poultry.class, Pet.class);  // with components

        Pet pet = (Pet) context.getBean("pet");
        System.out.println(pet);

        checkComponents((AbstractApplicationContext) context);
    }

    /**
     * @see org.springframework.context.annotation.Conditional
     * @see org.springframework.context.annotation.Profile equals to @Conditional(ProfileCondition.class)
     * @see FocusConfiguration#duck()
     */
    public static void conditional() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(FocusConfiguration.class);

        // simulate in a develop profile
        StandardEnvironment env = new StandardEnvironment();
        env.addActiveProfile("dev");
        context.setEnvironment(env);

        context.refresh();

        Environment environment = context.getBean(Environment.class);
        System.out.println(environment);

        Poultry duck = (Poultry) context.getBean("duck");
        System.out.println(duck);
    }

    public static void main(String[] args) {
//        configuration();
//        indexed();
//        jsr330();
//        annotationContext();
        conditional();
    }

}
