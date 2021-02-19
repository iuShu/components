package org.iushu.context.annotation;

import org.iushu.context.beans.Conductor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * @see org.springframework.context.annotation.ConfigurationClass wrap a @Configuration class in Spring
 *
 * @author iuShu
 * @since 2/19/21
 */
@Configuration
public class FocusConfiguration {

    /**
     * @see org.springframework.context.annotation.BeanMethod the class that Spring supports @Bean annotation
     */
    @Bean
    public Conductor defaultConductor() {
        Conductor conductor = new Conductor();
        conductor.setName("default");
        conductor.setTitle("staff");
        conductor.setLevel(1);
        conductor.start();
        return conductor;
    }

}
