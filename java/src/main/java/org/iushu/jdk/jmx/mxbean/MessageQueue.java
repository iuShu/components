package org.iushu.jdk.jmx.mxbean;

import java.util.Date;
import java.util.Queue;

/**
 * @author iuShu
 * @since 6/11/21
 */
public class MessageQueue implements MessageQueueMXBean {

    private volatile boolean started;
    private volatile boolean running;
    private Queue<Connection> connections;

    public MessageQueue(Queue<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public boolean start() {
        System.out.println("[MessageQueue] " + (started ? "restart" : "start"));
        started = true;
        running = true;
        return true;
    }

    @Override
    public boolean pause() {
        if (started && running) {
            System.out.println("[MessageQueue] paused");
            running = false;
            return true;
        }

        System.out.println("[MessageQueue] " + (!started ? "not running" : "already paused"));
        return false;
    }

    @Override
    public boolean shutdown() {
        if (started) {
            System.out.println("[MessageQueue] shutdown");
            running = false;
            started = false;
            return true;
        }

        System.out.println("[MessageQueue] not running");
        return false;
    }

    @Override
    public Connection getConnection() {
        Connection connection = connections.poll();
        if (connection == null)
            return null;

        connection.setConnectionTime(new Date());
        return connection;
    }

}
