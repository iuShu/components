package org.iushu.web;

import org.apache.catalina.startup.Tomcat;
import org.iushu.web.component.WebXmlConfiguration;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ProtocolResolver;

/**
 * @author iuShu
 * @since 4/6/21
 */
@EnableAutoConfiguration
@ComponentScan("org.iushu.web")
public class Application {

    /**
     * Spring Boot uses a different type of ApplicationContext for embedded servlet container support.
     * @see org.springframework.boot.web.context.WebServerApplicationContext
     * @see org.springframework.boot.web.context.ConfigurableWebServerApplicationContext
     * @see org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext core implementation
     * @see org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext annotation-based
     * @see org.springframework.boot.web.servlet.context.XmlServletWebServerApplicationContext xml-based
     *
     * @see ServletWebServerApplicationContext#refresh()
     * @see ServletWebServerApplicationContext#onRefresh()
     *
     * @see SpringApplication#createApplicationContext()
     * @see SpringApplication#DEFAULT_SERVLET_WEB_CONTEXT_CLASS
     */
    static void applicationContext() {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
//        checkComponents((AbstractApplicationContext) context);
        System.out.println(context.getClass().getName());
    }

    /**
     * NOTE: Annotated with @EnableWebMvc at Configuration class means invalid the auto-configured components.
     * @see org.springframework.web.servlet.config.annotation.EnableWebMvc
     * @see org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport only contains basic configuration
     *
     * Spring boot auto-configuration had annotated with @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
     * That means it only valid at the context that not contain anyother WebMvcConfigurationSupport bean.
     * @see WebMvcAutoConfiguration adding Springboot requires components for SpringMVC
     *
     * SpringMvc auto configuration components:
     * @see WebMvcAutoConfiguration
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
     *
     * Sample components:
     * @see org.springframework.web.servlet.ViewResolver
     * @see org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver
     * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver
     * @see SpringBootWebMvcComponents#contentNegotiatingViewResolver() for more details
     *
     * @see org.springframework.http.converter.HttpMessageConverter
     * @see org.springframework.boot.autoconfigure.http.HttpMessageConverters
     *
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#resourceHandlerRegistrationCustomizer
     * @see org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
     * @see SpringBootWebMvcComponents#staticResource() for more details
     */
    static void mvcConfiguration() {

    }

    /**
     * @see EmbeddedWebServerFactoryCustomizerAutoConfiguration
     * @see WebServerFactoryCustomizerBeanPostProcessor
     * @see WebXmlConfiguration#customizer(ServerProperties) for more web server customizer details
     * @see #customizer() for more working details about customizer component
     *
     * @see org.springframework.boot.web.server.WebServerFactoryCustomizer
     * @see TomcatWebServerFactoryCustomizer
     * @see org.springframework.boot.autoconfigure.web.embedded.JettyWebServerFactoryCustomizer
     * @see org.springframework.boot.autoconfigure.web.embedded.UndertowWebServerFactoryCustomizer
     *
     * @see org.springframework.boot.web.server.ConfigurableWebServerFactory
     * @see org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory
     * @see TomcatServletWebServerFactory
     * @see org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory
     *
     * @see ServletWebServerApplicationContext#refresh()
     * @see ServletWebServerApplicationContext#onRefresh()
     * @see ServletWebServerApplicationContext#createWebServer()
     * @see ServletWebServerApplicationContext#getWebServerFactory()
     * @see org.springframework.boot.web.server.WebServer
     * @see TomcatServletWebServerFactory#getWebServer
     * @see TomcatServletWebServerFactory#getTomcatWebServer(Tomcat)
     * @see org.springframework.boot.web.embedded.tomcat.TomcatWebServer#initialize()
     * @see org.apache.catalina.startup.Tomcat#start()
     */
    static void webServer() {

    }

    /**
     * How components combine together to work: *AutoConfiguration *Factory *Customizer *PostProcessor.
     * Sample described below:
     * @see ServletWebServerFactoryAutoConfiguration
     * @see TomcatServletWebServerFactory
     * @see TomcatWebServerFactoryCustomizer
     * @see WebServerFactoryCustomizerBeanPostProcessor
     *
     * AutoConfiguration -> PostProcessor -> Customizer -> Factory
     * @see ServletWebServerApplicationContext#refresh()
     * @see ServletWebServerFactoryAutoConfiguration import configuration
     * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration.EmbeddedTomcat#tomcatServletWebServerFactory
     * @see TomcatServletWebServerFactory be registered BeanDefinition(with FactoryBeanName & FactoryBeanMethod) in BeanFactory
     * @see ServletWebServerApplicationContext#onRefresh()
     * @see ServletWebServerApplicationContext#createWebServer()
     * @see ServletWebServerApplicationContext#getWebServerFactory()
     * @see ServletWebServerApplicationContext#getBean(String, Class) create web server factory by FactoryBeanName & FactoryBeanMethod
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(Object, String)
     * @see WebServerFactoryCustomizerBeanPostProcessor#postProcessBeforeInitialization(Object, String)
     * @see WebServerFactoryCustomizerBeanPostProcessor#getCustomizers()
     * @see WebServerFactoryCustomizerBeanPostProcessor#getWebServerFactoryCustomizerBeans()
     * @see org.springframework.boot.web.server.WebServerFactoryCustomizer#customize(WebServerFactory)
     * @see TomcatServletWebServerFactory#getWebServer(ServletContextInitializer...)
     *
     * More common working flow: AutoConfiguration -> Customizer -> Factory
     */
    static void customizer() {

    }

    /**
     * @see org.springframework.boot.web.servlet.ServletRegistrationBean
     * @see org.springframework.boot.web.servlet.FilterRegistrationBean
     * @see org.springframework.boot.web.servlet.ServletListenerRegistrationBean
     * @see org.springframework.boot.web.servlet.ServletContextInitializer parent interface
     *
     * @see WebXmlConfiguration#pumpkinServlet()
     * @see WebXmlConfiguration#pumpkinFilter()
     * @see WebXmlConfiguration#pumpkinListener()
     *
     * Componnet annotated registrations can be enabled by using servlet component scan.
     * @see org.springframework.boot.web.servlet.ServletComponentScan
     * @see javax.servlet.annotation.WebServlet
     * @see javax.servlet.annotation.WebFilter
     * @see javax.servlet.annotation.WebListener
     */
    static void servletCoreComponents() {

    }

    public static void checkComponents(AbstractApplicationContext context) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        System.out.println("Environment: " + context.getEnvironment().getClass().getName());

        for (BeanFactoryPostProcessor processor : context.getBeanFactoryPostProcessors())
            System.out.println("BeanFactoryPostProcessor: " + processor.getClass().getName());
        for (BeanPostProcessor processor : beanFactory.getBeanPostProcessors())
            System.out.println("BeanPostProcessor: " + processor.getClass().getName());
        for (ApplicationListener listener : context.getApplicationListeners())
            System.out.println("ApplicationListener: " + listener.getClass().getName());
        for (ProtocolResolver resolver : context.getProtocolResolvers())
            System.out.println("ProtocolResolver: " + resolver.getClass().getName());
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("Bean: " + name);
            System.out.println("      " + context.getBean(name).getClass().getName());
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void main(String[] args) {
        applicationContext();
    }

}
