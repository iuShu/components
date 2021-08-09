package org.iushu.project;

import org.iushu.project.bean.ConnectionMetadata;
import org.iushu.project.bean.FakeDataSource;
import org.iushu.project.bean.Staff;
import org.iushu.project.service.DepartmentService;
import org.iushu.project.service.StaffService;
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

import java.util.Arrays;
import java.util.Collection;

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
     * @see SpringBootComponents#prepareEnvironment() loading default configuration
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
     * 9. loading Bean into BeanFactory
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
     *
     * @see AutoConfigurationImportSelector#getConfigurationClassFilter()
     * @see org.springframework.boot.autoconfigure.AutoConfigurationImportFilter default 3 filters from spring.factories
     * @see SpringBootComponents#autoConfigurationImportFilter()
     */
    static void importSelector() {

    }

    /**
     * @see AnnotationComponents for more details
     * @see org.springframework.beans.factory.annotation.Value
     * @see org.springframework.context.annotation.PropertySource specified file properties
     * @see org.springframework.boot.context.properties.ConfigurationProperties global properties
     * @see org.springframework.context.annotation.ImportResource import spring xml configuration
     */
    static void springBootAnnotations() {

    }

    /**
     * Active specified profile (default application.properties/yml)
     *  1. configuring at application.properties: spring.profiles.active=dev
     *  2. adding program arguments at startup: --spring.profiles.active=dev
     *  3. adding VM arguments at startup: -Dspring.profiles.active=dev
     *
     * Loading *.properties configuration first, then *.yml
     * More details in loading environment:
     * @see SpringBootComponents#prepareEnvironment()
     *
     * @see org.springframework.context.annotation.Profile
     */
    static void profile() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

        ConfigurableEnvironment env = context.getEnvironment();
        System.out.println(Arrays.toString(env.getActiveProfiles()));

        ConnectionMetadata metadata = context.getBean(ConnectionMetadata.class);
        System.out.println(metadata);

        context.close();
    }

    /**
     * Run some specific code once the SpringApplication has started.
     * (after application startup but before it starts accepting traffic)
     *
     * @see org.springframework.boot.ApplicationRunner
     * @see org.springframework.boot.CommandLineRunner
     * @see ProjectConfiguration#customRunner()
     */
    static void runner() {

    }

    static void simpleDemonstrate() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

        StaffService service = context.getBean(StaffService.class);
        Staff staff = service.getStaff(40455);
        System.out.println(staff);

        ConnectionMetadata metadata = context.getBean(ConnectionMetadata.class);
        System.out.println(metadata);

//        org.iushu.web.Application.checkComponents((AbstractApplicationContext) context);
        context.close();
    }

    static void conditionalBean() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

        StaffService staffService = context.getBean(StaffService.class);
        System.out.println(staffService.getStaff(33));

        DepartmentService departmentService = context.getBean(DepartmentService.class);
        System.out.println(departmentService.getDepartment(88));

        context.close();
    }

    /**
     * To use @ConstructorBinding the class must be enabled using @EnableConfigurationProperties or configuration
     * property scanning. @ConstructorBinding can not be used with beans that created by the regular Spring mechanisms,
     * like beans annotated with @Bean, @Import and @Component, etc,.
     * (annotated @ConstructorBinding at constructor method if multiple constructors)
     * @see org.springframework.boot.context.properties.EnableConfigurationProperties
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesScan
     * @see org.springframework.boot.context.properties.ConstructorBinding
     * @see org.springframework.boot.context.properties.ConfigurationProperties
     * @see org.springframework.boot.context.properties.bind.DefaultValue place default value if no configuration found
     *
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder#BEAN_NAME
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder#FACTORY_BEAN_NAME
     *
     * @see org.iushu.project.bean.DataSourceProperties
     * @see org.iushu.project.bean.DataSourceProperties.DataSourceInfo
     * @see org.iushu.project.bean.FakeDataSource
     *
     * Validator on property class and fields.
     * @see org.springframework.boot.context.properties.EnableConfigurationProperties#VALIDATOR_BEAN_NAME
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder#VALIDATOR_BEAN_NAME
     */
    static void configurationProperties() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

        FakeDataSource dataSource = context.getBean(FakeDataSource.class);
        System.out.println(dataSource.getProperties());

//        org.iushu.web.Application.checkComponents((AbstractApplicationContext) context);
        context.close();
    }

    public static void main(String[] args) {
//        profile();
//        simpleDemonstrate();
//        conditionalBean();
        configurationProperties();
    }

}
