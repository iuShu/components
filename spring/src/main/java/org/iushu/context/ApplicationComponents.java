package org.iushu.context;

import org.iushu.context.annotation.FocusConfiguration;
import org.iushu.context.beans.Conductor;
import org.iushu.context.beans.ConductorEvent;
import org.iushu.context.beans.Microphone;
import org.iushu.context.components.FocusApplicationListener;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.event.DefaultEventListenerFactory;
import org.springframework.context.event.EventListenerMethodProcessor;
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

        Conductor conductor = context.getBean(Conductor.class);
        System.out.println(conductor);
    }

    /**
     * TODO How CommonAnnotationBeanPostProcessor integrating the JNDI module in Spring
     *
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor supports @PreDestroy and @PostConstruct
     * @see org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor the superclass that actually supports the annotations
     */
    private static void commonAnnotationBeanPostProcessor() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.registerBean(Microphone.class);
        context.refresh();

        Microphone microphone = context.getBean(Microphone.class);
        System.out.println(microphone);

        context.close();
    }

    /**
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor responsible for processing @Autowired & @Value
     * @see org.iushu.expression.Application#beanDefinition()
     */
    private static void autowiredAnnotationBeanPostProcessor() {

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
//        configurationClassPostProcessor();
        commonAnnotationBeanPostProcessor();
//        eventListenerMethodProcessor();
    }

}
