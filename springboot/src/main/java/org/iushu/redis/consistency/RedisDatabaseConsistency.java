package org.iushu.redis.consistency;

import org.iushu.redis.RedisCustomConfiguration;
import org.iushu.web.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author iuShu
 * @since 5/26/21
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {RedisCustomConfiguration.class})
public class RedisDatabaseConsistency {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RedisDatabaseConsistency.class);
        Application.checkComponents((AbstractApplicationContext) context);
    }

}
