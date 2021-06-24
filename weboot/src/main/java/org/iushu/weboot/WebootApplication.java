package org.iushu.weboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TODO
 *      session token
 *      auto-login by redis
 *      parameter validation
 *      auto-reloadable properties
 *      preload access limit annotation
 *
 *      role and user service
 *
 * @author iuShu
 * @since 6/24/21
 */
@SpringBootApplication
public class WebootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebootApplication.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames())
            System.out.println(beanDefinitionName + "\t" + context.getBean(beanDefinitionName).getClass().getName());
    }

}
