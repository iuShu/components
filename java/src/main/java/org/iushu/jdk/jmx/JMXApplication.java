package org.iushu.jdk.jmx;

import org.iushu.jdk.Utils;
import org.iushu.jdk.jmx.mbean.Resource;
import org.iushu.jdk.jmx.mbean.ResourceMBean;
import org.iushu.jdk.jmx.mxbean.Connection;
import org.iushu.jdk.jmx.mxbean.MessageQueue;
import org.iushu.jdk.jmx.mxbean.MessageQueueMXBean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author iuShu
 * @since 6/11/21
 */
public class JMXApplication {

    /**
     * MBean (Managed Bean)
     *
     * @see ManagementFactory#GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE  GarbageCollector
     * @see ManagementFactory#RUNTIME_MXBEAN_NAME                   Runtime
     * @see ManagementFactory#THREAD_MXBEAN_NAME                    Threading
     * @see ManagementFactory#CLASS_LOADING_MXBEAN_NAME             Classloading
     * @see ManagementFactory#MEMORY_MXBEAN_NAME                    Memory
     * ...
     *
     * Threading
     * @see java.lang.management.PlatformManagedObject
     * @see java.lang.management.ThreadMXBean
     * @see com.sun.management.ThreadMXBean
     * @see sun.management.ThreadImpl
     *
     * MXBeans
     *  MXBean is a type of MBean that references only a predefined set of data types. In this way, you can be sure that
     *  your MBean will be usable by any client, including remote clients, without any requirement that the client have
     *  access to MODEL-specific classes representing the types of your MBeans. MXBeans provide a convenient way to
     *  bundle related values together, without requiring clients to be specially configured to handle the bundles.
     * @see #introducingMXBean()
     */
    static void builtInMBean() {

    }

    /**
     * open JMC to check MBean running status after program ran
     *
     * MBean Naming Regulation
     *  interface           AppleMBean
     *  implementation      Apple
     *
     * @see ManagementFactory
     * @see MBeanServer
     * @see com.sun.jmx.mbeanserver.JmxMBeanServer
     */
    static void standardMBean() {
        ResourceMBean resourceMBean = new Resource(17, "MySQL-dragon", "/etc/conf/*.sql");

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();                       // JMX Agent
        System.out.println("mBeanServer: " + mBeanServer.getClass().getName());
        try {
            ObjectName objectName = new ObjectName("org.iushu.jdk.jmx:type=Resource");     // MBean's name
            mBeanServer.registerMBean(resourceMBean, objectName);
            System.out.println("waiting JMC open to check MBean status");
            Utils.sleep(100000);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    /**
     * MBeanServer will return a model randomly
     * model in this case is Connection class and has 5 objects
     *
     * @see #builtInMBean() introduction
     */
    static void introducingMXBean() {
        Queue<Connection> connections = new LinkedList<>();
        connections.add(new Connection("a28A21mx058", "asia-04"));
        connections.add(new Connection("asa68200Xlx", "asia-05"));
        connections.add(new Connection("hk217906", "hk-01"));
        connections.add(new Connection("tw20087113", "tw-12"));
        connections.add(new Connection("az-mn9101", "azure-01"));
        MessageQueueMXBean mxBean = new MessageQueue(connections);
        mxBean.start();

        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("org.iushu.jdk.jmx:type=MessageQueue");
            mBeanServer.registerMBean(mxBean, objectName);
            System.out.println("waiting JMC open to check MBean status");
            Utils.sleep(100000);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        standardMBean();
        introducingMXBean();
    }

}
