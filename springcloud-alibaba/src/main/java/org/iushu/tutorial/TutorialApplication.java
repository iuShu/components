package org.iushu.tutorial;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author iuShu
 * @since 8/2/21
 */
@SpringCloudApplication
@EnableDiscoveryClient
public class TutorialApplication {

    /**
     * @see NacosDiscoveryProperties
     * @see NacosServiceRegistryAutoConfiguration
     */
    static void configuration() {

    }

    /**
     * Prepared Bean
     * @see NacosServiceRegistryAutoConfiguration#nacosServiceRegistry(NacosDiscoveryProperties)
     * @see NacosServiceRegistryAutoConfiguration#nacosAutoServiceRegistration(NacosServiceRegistry, AutoServiceRegistrationProperties, NacosRegistration)
     *
     * Process flow
     * @see WebServerInitializedEvent event publish when the WebServer is ready
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(ApplicationEvent)
     * @see NacosAutoServiceRegistration
     * @see AbstractAutoServiceRegistration#bind(WebServerInitializedEvent)
     * @see AbstractAutoServiceRegistration#start()
     * @see AbstractAutoServiceRegistration#register()
     * @see NacosServiceRegistry#register(Registration)
     * @see NacosServiceManager#getNamingService(Properties)
     * @see NamingService#registerInstance(String, String, Instance)
     * @see NacosNamingService#registerInstance(String, String, Instance)
     */
    static void registry() {

    }

    /**
     * @see AnnotationConfigServletWebServerApplicationContext
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TutorialApplication.class);
        System.out.println(">> " + context.getClass().getName());
        System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

}
