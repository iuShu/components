package com.iushu.jms.activemq;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Enabled ActiveMQ(v5.15.8) JMX Service:
 *      1. conf / activemq.xml
 *          <broker ... ... brokerName="xxx">
 *         change to followings:
 *          <broker ... ... brokerName="xxx" useJmx="true">
 *
 *      2. conf / activemq.xml
 *          <managementContext createConnector="false"/>
 *         change to followings:
 *          <managementContext createConnector="true" connectorPort="1099"/>
 *
 *      3. run activemq script
 *          ./activemq start
 *
 *      4. use jconsole try to connect activemq.
 *          remote url: service:jmx:rmi:///jndi/rmi://<your host>:1099/jmxrmi
 *
 *      5. work with java code.
 *
 * Created by iuShu on 19-2-25
 */
public class JMXService {

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.61.137:1099/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();

        ObjectName name = new ObjectName("org.apache.activemq:type=Broker,brokerName=Mischief");
        BrokerViewMBean mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);

        for(ObjectName queueName : mBean.getQueues()) {
            QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
            System.out.println(">>> " + queueMBean.getName());
            System.out.println("    Number Of Pending Messages: " + queueMBean.getQueueSize());
            System.out.println("    Number Of Consumers: " + queueMBean.getConsumerCount());
            System.out.println("    Messages Enqueued: " + queueMBean.getDequeueCount() );
            System.out.println("    Messages Dequeued: " + queueMBean.getDequeueCount() );
        }
    }

}
