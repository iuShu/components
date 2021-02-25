package org.iushu.context.annotation;

import org.iushu.context.annotation.beans.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Import
 *
 * @author iuShu
 * @since 2/19/21
 */
@Configuration
@ComponentScan(basePackages = "org.iushu.context.annotation")
public class FocusConfiguration {

    @Bean
    public Pet defaultPet() {
        Pet pet = new Pet();
        pet.setName("Giraffe");
        return pet;
    }

    @Bean(initMethod = "birth")
    public Poultry rooster() {
        Poultry rooster = new Poultry();
        rooster.setName("rooster");
        return rooster;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Hen hen() {
        Hen hen = new Hen();
        hen.setName("hen");
        return hen;
    }

    @Bean
    public Egg egg() {
        return new Egg() {
            @Override
            public Animal layEgg() {
                return hen();
            }
        };
    }

    @Bean
    public static Object postProcessor() {
        return new Object();
    }

    /**
     * @see Conditional
     * @see Profile equals to @Conditional(ProfileCondition.class)
     */
    @Bean
    @Profile("dev")
    public Poultry duck() {
        Poultry duck = new Poultry();
        duck.setName("Donald Duck");
        return duck;
    }

}
