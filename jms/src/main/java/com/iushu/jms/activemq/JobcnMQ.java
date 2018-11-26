package com.iushu.jms.activemq;

import com.iushu.jms.Utils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;
import java.util.Date;

/**
 * Created by iuShu on 18-10-9
 */
public class JobcnMQ {

    public static final String MQ_URL = "tcp://192.168.61.200:61616";
    public static final String USER = "admin";
    public static final String PASSWORD = "admin";

    public static final String QUEUE_LOGIN = "loginTraceQueue";
    public static final String QUEUE_REGISTER = "registerTraceQueue";
    public static final String QUEUE_SEARCH = "searchTraceQueue";
    public static final String QUEUE_RESUME = "resumeTraceQueue";
    public static final String QUEUE_FILE = "filesTraceQueue";

    public static final long DEFAULT_RECEIVE_TIMEOUT = 2000L;

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQ_URL);
        Connection conn = factory.createConnection(USER, PASSWORD);
        conn.start();

        Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);

        try {

//            glanceMessage(session, QUEUE_SEARCH, 20);
            glanceMessage(session, QUEUE_LOGIN, 300);
//            glanceMessage(session, QUEUE_REGISTER, 10);
//            glanceMessage(session, QUEUE_RESUME, 10);
//            glanceMessage(session, QUEUE_FILE, 10);

        } catch (Exception e) {
            throw e;
        } finally {
//            session.commit(); // take message from queue
            session.rollback(); // retains message in queue

            conn.close();
            session.close();
        }

        System.err.println(">>> end");
    }


    private static void glanceMessage(Session session, String queueName, int scale) throws JMSException {
        Destination dest = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(dest);

        ActiveMQObjectMessage message;
        for (int i : Utils.getIntArray(scale)) {
            message = (ActiveMQObjectMessage) consumer.receive(DEFAULT_RECEIVE_TIMEOUT);
            if (message == null)
                return;
//            properties(message);
            System.out.println(message.getMessageId());
            System.out.println(message.getObject());
        }
    }

    private static void properties(ActiveMQObjectMessage message) {
        System.out.println("mime type: " + message.getJMSXMimeType());
        System.out.println("arrival: " + message.getArrival());
        System.out.println("broker in time: " + new Date(message.getBrokerInTime()));
        System.out.println("broker out time: " + new Date(message.getBrokerOutTime()));
        System.out.println("commandId: " + message.getCommandId());
        System.out.println("correlationId: " + message.getCorrelationId());
        System.out.println("expiration: " + message.getExpiration());
        System.out.println("from: " + message.getFrom());
        System.out.println("groupId: " + message.getGroupID());
        System.out.println("group sequence: " + message.getGroupSequence());
        System.out.println("jms timestamp: " + message.getJMSTimestamp());

        System.out.println();
    }

}
