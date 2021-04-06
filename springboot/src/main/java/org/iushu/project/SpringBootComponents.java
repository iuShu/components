package org.iushu.project;

/**
 * @author iuShu
 * @since 4/6/21
 */
public class SpringBootComponents {

    /**
     * @see org.springframework.boot.SpringApplication#initializers
     *
     * Default initializer during startup
     * @see org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer
     * @see org.springframework.boot.context.ContextIdApplicationContextInitializer
     * @see org.springframework.boot.context.config.DelegatingApplicationContextInitializer
     * @see org.springframework.boot.rsocket.context.RSocketPortInfoApplicationContextInitializer
     * @see org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer
     * @see org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer
     * @see org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
     *
     * This initializers are configured in built-in configuration, listed following orderly:
     *  spring-boot/META-INF/spring.factories (first 5)
     *  spring-beans/META-INF/spring.factories
     *  spring-boot-autoconfigure/META-INF/spring.factories (last 2)
     */
    static void contextInitializer() {

    }

    /**
     * @see org.springframework.boot.SpringApplication#listeners
     *
     * Default listeners during startup and also are configured in built-in configuration.
     * @see org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor
     * @see org.springframework.boot.context.config.ConfigFileApplicationListener
     * @see org.springframework.boot.context.config.AnsiOutputApplicationListener
     * @see org.springframework.boot.context.logging.LoggingApplicationListener
     * @see org.springframework.boot.context.logging.ClasspathLoggingApplicationListener
     * @see org.springframework.boot.autoconfigure.BackgroundPreinitializer
     * @see org.springframework.boot.context.config.DelegatingApplicationListener
     * @see org.springframework.boot.builder.ParentContextCloserApplicationListener
     * @see org.springframework.boot.ClearCachesApplicationListener
     * @see org.springframework.boot.context.FileEncodingApplicationListener
     * @see org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
     */
    static void listeners() {

    }

    public static void main(String[] args) {

    }

}
