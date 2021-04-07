package org.iushu.project;

import org.springframework.boot.diagnostics.FailureAnalysis;

import java.util.Collection;

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

    /**
     * Responsible for multicast application event
     * @see org.springframework.boot.SpringApplicationRunListener
     * @see org.springframework.boot.SpringApplicationRunListeners composite
     *
     * Default implementation
     * @see org.springframework.boot.context.event.EventPublishingRunListener
     *
     * @see org.springframework.boot.context.event.ApplicationStartingEvent
     * @see org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
     * @see org.springframework.boot.context.event.ApplicationContextInitializedEvent
     * @see org.springframework.boot.context.event.ApplicationPreparedEvent
     * @see org.springframework.boot.context.event.ApplicationStartedEvent
     * @see org.springframework.boot.context.event.ApplicationReadyEvent
     * @see org.springframework.boot.context.event.ApplicationFailedEvent
     */
    static void springApplicationRunListener() {

    }

    /**
     * @see org.springframework.boot.diagnostics.FailureAnalyzers composite
     * @see org.springframework.boot.diagnostics.FailureAnalyzer
     *
     * @see org.springframework.boot.diagnostics.FailureAnalysis failure analyzied info bean
     * @see org.springframework.boot.diagnostics.FailureAnalysisReporter strategies to report an analysis
     *
     * Failure process in SpringBoot
     * @see org.springframework.boot.SpringApplication#handleRunFailure
     * @see org.springframework.boot.SpringApplication#reportFailure(Collection, Throwable)
     * @see org.springframework.boot.SpringApplication#reportFailure
     * @see org.springframework.boot.SpringBootExceptionReporter#reportException(Throwable)
     * @see org.springframework.boot.diagnostics.FailureAnalyzers#reportException(Throwable)
     * @see org.springframework.boot.diagnostics.FailureAnalysisReporter#report(FailureAnalysis)
     * @see org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter#report(FailureAnalysis) default implementation
     *
     * Default implementations(19) during startup, mostly have their binding exception.
     * @see org.springframework.boot.context.properties.NotConstructorBoundInjectionFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.BeanCurrentlyInCreationFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.BeanDefinitionOverrideFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.BindFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.BindValidationFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.UnboundConfigurationPropertyFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.ConnectorStartFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.NoSuchMethodFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.NoUniqueBeanDefinitionFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.PortInUseFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyNameFailureAnalyzer
     * @see org.springframework.boot.diagnostics.analyzer.InvalidConfigurationPropertyValueFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.data.redis.RedisUrlSyntaxFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.diagnostics.analyzer.NoSuchBeanDefinitionFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.flyway.FlywayMigrationScriptMissingFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceBeanCreationFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryBeanCreationFailureAnalyzer
     * @see org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryFailureAnalyzer
     */
    static void failureAnalyzers() {

    }

    public static void main(String[] args) {

    }

}