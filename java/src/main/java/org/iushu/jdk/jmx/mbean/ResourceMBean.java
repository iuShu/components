package org.iushu.jdk.jmx.mbean;

/**
 * JMX module
 *
 * @author iuShu
 * @since 6/11/21
 */
public interface ResourceMBean {

    int getQuantity();

    String getResourceName();

    String getResourcePath();

    void loadResource();

}
