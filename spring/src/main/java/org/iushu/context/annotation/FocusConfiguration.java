package org.iushu.context.annotation;

import org.iushu.context.annotation.beans.Pet;
import org.iushu.context.annotation.beans.Poultry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

}
