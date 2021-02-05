package com.iushu.jms.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.Closeable;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * Created by iuShu on 19-2-25
 */
public class ActiveMQContext {

    private static Connection connection = null;

    public static Session getSession(String url, String user, String password) {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
            connection = factory.createConnection(user, password);
            connection.start();
            return connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MessageProducer getProducer(Session session, String queue) {
        try {
            Destination destination = session.createQueue(queue);
            return session.createProducer(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MessageConsumer getConsumer(Session session, String queue) {
        try {
            Destination destination = session.createQueue(queue);
            return session.createConsumer(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendText(MessageProducer producer, String text) {
        try {
            ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText(text);
            producer.send(textMessage);
            producer.close();   // session remove producer and fast to send cache message out.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Message consumer(MessageConsumer consumer, long timeout) {
        try {
            return consumer.receive(timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Message glanceMessage(MessageConsumer consumer) {
        try {
            Message message = consumer.receive();
            ActiveMQMessageConsumer messageConsumer = (ActiveMQMessageConsumer) consumer;
            messageConsumer.rollback();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void commit(Session session) {
        try {
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Session session) {
        try {
            if (session == null)
                return;
            session.close();
            connection.close(); // shutdown all
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(MessageProducer producer) {
        try {
            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void close(MessageConsumer consumer) {
        try {
            consumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
