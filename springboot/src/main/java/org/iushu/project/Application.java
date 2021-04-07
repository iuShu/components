package org.iushu.project;

import org.iushu.project.bean.Staff;
import org.iushu.project.service.StaffService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collection;
import java.util.Set;

/**
 * @author iuShu
 * @since 4/6/21
 */
@SpringBootApplication
public class Application {

    /**
     * SpringBootApplication is often placed on your main class, and it implicitly defines
     * the base "search package" for certain items. This annotation is composite of:
     * @see org.springframework.boot.SpringBootConfiguration equivalent to @Configuration
     * @see org.springframework.boot.autoconfigure.EnableAutoConfiguration
     * @see org.springframework.context.annotation.ComponentScan
     */
    static void springBootApplication() {

    }

    /**
     * @see SpringApplication#run(String...) core startup method
     *
     * 1. loading ApplicationContextInitializer from built-in configuration.
     * @see SpringApplication#getSpringFactoriesInstances(Class)
     * @see org.springframework.core.io.support.SpringFactoriesLoader#loadFactoryNames(Class, ClassLoader)
     * @see SpringApplication#setInitializers(Collection)
     * @see org.springframework.context.ApplicationContextInitializer
     * @see SpringBootComponents#contextInitializer() for more details
     *
     * 2. loading ApplicationListener from built-in configuration
     * @see SpringApplication#getSpringFactoriesInstances(Class)
     * @see org.springframework.core.io.support.SpringFactoriesLoader#loadFactoryNames(Class, ClassLoader)
     * @see SpringApplication#setListeners(Collection)
     * @see SpringBootComponents#listeners() for more details
     *
     * 3. loading SpringApplicationRunListener from built-in configuration
     * @see SpringApplication#getRunListeners(String[])
     * @see org.springframework.boot.SpringApplicationRunListener
     * @see SpringBootComponents#springApplicationRunListener() for more details
     *
     * 4. prepare Environment
     * @see SpringApplication#prepareEnvironment
     * @see SpringApplication#getOrCreateEnvironment()
     *
     * 5. prepare banner to be printed
     * @see SpringApplication#printBanner(ConfigurableEnvironment)
     * @see org.springframework.boot.Banner
     * @see org.springframework.boot.SpringBootBanner default banner
     *
     * 6. create ApplicationContext
     * @see SpringApplication#createApplicationContext()
     * @see SpringApplication#DEFAULT_CONTEXT_CLASS
     * @see org.springframework.context.annotation.AnnotationConfigApplicationContext default context in SpringBoot
     *
     * 7. loading SpringBootExceptionReporter from built-in configuration
     * @see org.springframework.boot.SpringBootExceptionReporter
     * @see org.springframework.boot.diagnostics.FailureAnalyzers composite
     * @see org.springframework.boot.diagnostics.FailureAnalyzer default 19
     * @see org.springframework.boot.diagnostics.FailureAnalysis
     * @see org.springframework.boot.diagnostics.FailureAnalysisReporter
     * @see SpringBootComponents#failureAnalyzers() for more details
     *
     * 8. prepare ApplicationContext
     * @see SpringApplication#prepareContext
     * @see SpringApplication#postProcessApplicationContext
     * @see SpringApplication#applyInitializers
     * @see SpringApplication#lazyInitialization
     * @see org.springframework.boot.LazyInitializationBeanFactoryPostProcessor
     *
     * 9. loading Bean ino BeanFactory
     * @see SpringApplication#load(ApplicationContext, Object[])
     * @see org.springframework.boot.BeanDefinitionLoader#load()
     * @see org.springframework.boot.BeanDefinitionLoader#annotatedReader
     * @see org.springframework.context.annotation.AnnotatedBeanDefinitionReader
     * @see org.springframework.boot.BeanDefinitionLoader#xmlReader
     * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
     * @see org.springframework.boot.BeanDefinitionLoader#scanner
     * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
     *
     * 10. refresh Context
     * @see SpringApplication#refreshContext(ConfigurableApplicationContext)
     * @see SpringApplication#refresh(ApplicationContext)
     * @see SpringApplication#refresh(ConfigurableApplicationContext)
     * @see ConfigurableApplicationContext#refresh()
     */
    static void startup() {

    }

    /**
     * @see ConfigurationClassPostProcessor
     * @see AutoConfigurationImportSelector
     * @see AutoConfigurationImportSelector.AutoConfigurationGroup
     *
     * Loading components from built-in configuration: spring-boot/META-INF/spring.factories
     * @see AutoConfigurationImportSelector#getAutoConfigurationEntry(AnnotationMetadata)
     * @see AutoConfigurationImportSelector#getCandidateConfigurations(AnnotationMetadata, AnnotationAttributes)
     * @see AutoConfigurationImportSelector#getSpringFactoriesLoaderFactoryClass()
     * @see org.springframework.core.io.support.SpringFactoriesLoader#loadFactoryNames(Class, ClassLoader)
     */
    static void importSelector() {

    }

    static void simpleDemonstrate() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        StaffService service = context.getBean(StaffService.class);
        Staff staff = service.getStaff(40455);
        System.out.println(staff);

        org.iushu.web.Application.checkComponents((AbstractApplicationContext) context);
        context.close();
    }

    public static void main(String[] args) {
        simpleDemonstrate();
    }

}
