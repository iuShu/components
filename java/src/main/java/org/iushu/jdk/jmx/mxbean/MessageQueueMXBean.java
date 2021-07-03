package org.iushu.jdk.jmx.mxbean;

/**
 * @author iuShu
 * @since 6/11/21
 */
public interface MessageQueueMXBean {

    boolean start();

    boolean pause();

    boolean shutdown();

    Connection getConnection();

}
