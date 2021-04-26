package org.iushu.jdk.lock;

import org.iushu.jdk.Utils;

/**
 * Semaphore's take and release can be used by multiple threads.
 * Lock's lock and unlock can only be used by the same thread.
 *
 * @author iuShu
 * @since 4/25/21
 */
public class SemaphoreCase {

    private boolean signal = false;  // equals signals = 1 and can be used as Lock

    private int signals = 0;
    private int limit = signals + 1;

    public SemaphoreCase() {}

    public SemaphoreCase(int limit) {
        this.limit = limit;
    }

    synchronized void take() {
        signal = true;
        notify();
    }

    synchronized void release() {
        try {
            while (!signal)
                wait();
            signal = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void takeOne() {
        ++signals;
        notify();
    }

    synchronized void releaseOne() {
        try {
            while (signals == 0)
                wait();
            --signals;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void takeOneLimit() {
        try {
            while (signals == limit)
                wait();

            ++signals;
            notify();   // notify release threads
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void releaseOneLimit() {
        try {
            while (signals == 0)
                wait();

            --signals;
            notify();   // notify take threads
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see #signal
     * @see #take()
     * @see #release()
     */
    static void simpleSemaphoreCase() {
        SemaphoreCase semaphoreCase = new SemaphoreCase();
        String message = " <encrypted message>";
        Runnable sender = () -> {
            System.out.println(Thread.currentThread().getName() + message);
            semaphoreCase.take();   // notify message already sent
        };
        Runnable receiver = () -> {
            System.out.println("receiver ready");
            semaphoreCase.release();    // waiting notification of message sent
            System.out.println(Thread.currentThread().getName() + message);
        };

        new Thread(receiver, "receiving").start();
        Utils.sleep(1000);
        new Thread(sender, "sending").start();
    }

    /**
     * @see #signals
     * @see #takeOne()
     * @see #releaseOne()
     */
    static void countableSemaphoreCase() {

    }



    public static void main(String[] args) {
        simpleSemaphoreCase();
    }

}
