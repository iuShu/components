package org.iushu.web.component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author iuShu
 * @since 4/12/21
 */
public class PumpkinEventListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[ctx-init] " + sce.getServletContext().getClass().getName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ctx-dest] " + sce.getServletContext().getClass().getName());
    }
}
