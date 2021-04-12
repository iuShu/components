package org.iushu.project;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;

/**
 * @author iuShu
 * @since 4/8/21
 */
public class AnnotationComponents {

    /**
     * Two configuration injecting approaches
     * @see org.springframework.beans.factory.annotation.Value
     * @see org.springframework.boot.context.properties.ConfigurationProperties
     *
     * Single field injection and support SpEL expression
     * @see org.springframework.beans.factory.annotation.Value
     *
     * Batch field injection in class, support JSR303 data validation and complicated type injection
     * @see org.springframework.boot.context.properties.ConfigurationProperties
     * @see org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration
     * @see org.springframework.boot.context.properties.EnableConfigurationProperties
     * @see org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar imported by EnableConfigurationProperties
     *
     * Working flow
     * @see org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar#registerInfrastructureBeans
     * @see ConfigurationPropertiesBindingPostProcessor a BeanPostProcessor
     * @see ConfigurationPropertiesBindingPostProcessor#postProcessBeforeInitialization
     * @see ConfigurationPropertiesBindingPostProcessor#bind(ConfigurationPropertiesBean) bind field values from PropertySource
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder#bind(ConfigurationPropertiesBean)
     * @see ConfigurationPropertiesBean wrapped the raw bean to be binded
     */
    static void configurationValue() {

    }

}
