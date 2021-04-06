package org.iushu.project;

import org.iushu.project.bean.Staff;
import org.iushu.project.service.StaffService;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;

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
     *
     * 3.
     */
    static void startup() {

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
