package org.iushu.web.component;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author iuShu
 * @since 4/12/21
 */
@Configuration
public class WebXmlConfiguration {

    /**
     * @see WebServerFactoryCustomizerBeanPostProcessor handle customizers
     * @see TomcatServletWebServerFactory
     * @see TomcatServletWebServerFactoryCustomizer
     *
     * @see ServerProperties
     * @see ServletWebServerFactoryAutoConfiguration
     * @param properties import from ServletWebServerFactoryAutoConfiguration @EnableConfigurationProperties
     */
    @Bean
    public TomcatServletWebServerFactoryCustomizer customizer(ServerProperties properties) {
        return new TomcatServletWebServerFactoryCustomizer(properties) {
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.setPort(80);
                factory.setUriEncoding(Charset.forName("UTF-8"));
                // ...
            }
        };
    }

    /**
     * Register servlet to server, like listed belows:
     *
     * @see ServletRegistrationBean
     * @see DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration
     * @see DispatcherServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<PumpkinServlet> pumpkinServlet() {
        PumpkinServlet servlet = new PumpkinServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(servlet);
        registrationBean.addUrlMappings("/pumpkin");
        return registrationBean;
    }

    /**
     * Register filter to server, like listed belows:
     *
     * @see FilterRegistrationBean
     * @see ServletWebServerFactoryAutoConfiguration#forwardedHeaderFilter()
     * @see org.springframework.web.filter.ForwardedHeaderFilter
     */
    @Bean
    public FilterRegistrationBean<PumpkinFilter> pumpkinFilter() {
        PumpkinFilter filter = new PumpkinFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/pumpkin");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean pumpkinListener() {
        PumpkinEventListener listener = new PumpkinEventListener();
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean(listener);
        registrationBean.setListener(listener);
        return registrationBean;
    }

}