package org.iushu.xss;

import org.iushu.xss.components.SecureFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author iuShu
 * @since 6/8/21
 */
@Configuration
public class SecureConfiguration {

    @Bean
    public FilterRegistrationBean<SecureFilter> filterRegistrationBean() {
        FilterRegistrationBean<SecureFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(secureFilter());
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

    @Bean
    public SecureFilter secureFilter() {
        return new SecureFilter();
    }

}
