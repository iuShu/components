package org.iushu.project;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * The SPI implementation, auto-detected by Servlet 3.0 container.
 * More details about WebApplicationInitializer.
 * @see org.iushu.workflow.Application#servlet3Startup()
 *
 * @author iuShu
 * @since 3/29/21
 */
public class ProjectApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ProjectConfiguration.class);   // configuration

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("defaultDispatcherServlet", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
