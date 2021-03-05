package org.iushu;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * @author iuShu
 * @since 3/5/21
 */
public class ConnectionMetaData {

    public static final String JDBC_DRIVER;
    public static final String JDBC_URL;
    public static final String JDBC_USER;
    public static final String JDBC_PASSWORD;

    static {
        Resource resource = new DefaultResourceLoader().getResource("classpath:org/iushu/jdbc.properties");
        PropertySource propertySource = null;
        try {
            propertySource = new ResourcePropertySource(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JDBC_DRIVER = propertySource.getProperty("jdbc.driver").toString();
        JDBC_URL = propertySource.getProperty("jdbc.url").toString();
        JDBC_USER = propertySource.getProperty("jdbc.user").toString();
        JDBC_PASSWORD = propertySource.getProperty("jdbc.password").toString();
    }

}
