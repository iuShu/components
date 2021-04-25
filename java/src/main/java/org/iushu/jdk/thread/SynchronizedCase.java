package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;
import org.iushu.jdk.lock.SynchronizedLockCase;

/**
 * Be aware of the monitor lock holding by the synchronized method/block
 *
 * @author iuShu
 * @since 4/20/21
 */
public class SynchronizedCase {

    synchronized void wait0() {
        try {
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void notify0() {
        synchronized (this) {
            notify();
        }
    }

    static void classMonitor() {
        SynchronizedCase synchronizedCase = new SynchronizedCase();
        new Thread(synchronizedCase::wait0, "waiter").start();
        new Thread(synchronizedCase::notify0, "notifier").start();
    }

    static void objectMonitor() {

    }

    public static void main(String[] args) {

    }

}
