package com.iushu.jms.activemq;

import javax.jms.MessageProducer;
import javax.jms.Session;

import static com.iushu.jms.activemq.ActiveMQContext.*;

/**
 * An localhost ActiveMQ instance which broker name was Mischief.
 *
 * Created by iuShu on 19-2-25
 */
public class Mischief {

    public static String host = "192.168.61.137";
    public static String url = "tcp://" + host + ":61616";  // default OpenWire protocol
    public static String stomp = "stomp://" + host + ":61613";
    public static String ws = "ws://" + host + ":61614";
    public static String amqp = "amqp://" + host + ":5672";
    public static String mqtt = "mqtt://" + host + ":1883";

    public static String user = "admin";
    public static String password = "admin";

    public static void main(String[] args) {

        String queue = "appointment";

        send(url, queue, "The boss was intrigued by employee's creative idea at the meeting.");

    }

    public static void send(String url, String queue, String text) {
        Session session = getSession(url, user, password);
        MessageProducer producer = getProducer(session, queue);

        sendText(producer, text);
        commit(session);

        close(producer);
        close(session);
    }

}
