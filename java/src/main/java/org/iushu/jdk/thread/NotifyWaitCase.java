package org.iushu.jdk.thread;

import java.util.Random;

import static org.iushu.jdk.Utils.sleep;

/**
 * Do not use global or String constant as lock
 *
 * @author iuShu
 * @since 4/22/21
 */
public class NotifyWaitCase {

    void wait0() {
        System.out.println(Thread.currentThread().getName() + " wait");
        synchronized (this) {
            try {
                this.wait();    // Release all holding monitor locks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // be notified here then try to reacquire required monitor locks

            /*
             * threads are going to start lock contention if multiple being notified by notifyAll().
             * only one thread reacquired its required locks and quit wait() to run continue.
             */
        }
    }

    void notify0() {
        System.out.println(Thread.currentThread().getName() + " notify");
        synchronized (this) {   // acquire monitor lock
            this.notify();
        }
        // release monitor lock
    }

    boolean signalled = false;

    void safeWait() {
        System.out.println(Thread.currentThread().getName() + " safe wait");
        synchronized (this) {
            if (!signalled) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            signalled = false;  // notify signal processed
        }
    }

    void safeNotify() {
        System.out.println(Thread.currentThread().getName() + " safe notify");
        synchronized (this) {
            signalled = true;
            this.notify();
        }
    }

    /**
     * NOTE: missed signal risk in this case
     * @see #missedSignal() missed signal case
     * @see #resolveMissedSignal() resolve missed signal issue
     */
    static void threadCommunication() {
        final NotifyWaitCase notifyWaitCase = new NotifyWaitCase();
        Thread producer = new Thread(() -> {
            Random random = new Random();
            while (true) {
                sleep(random.nextInt(1000));
                System.out.println("produced a keyboard");
                notifyWaitCase.notify0();   // notify consumer to sell a keyboard
                notifyWaitCase.wait0();     // wait to produce till a keyboard sold
            }
        }, "producer");
        Thread consumer = new Thread(() -> {
            Random random = new Random();
            while (true) {
                notifyWaitCase.wait0();     // waiting producer produce a keyboard
                sleep(random.nextInt(1000));
                System.out.println("sold a keyboard");
                notifyWaitCase.notify0();   // notify producer to produce next
            }
        }, "consumer");
        producer.start();
        consumer.start();
    }

    /**
     * If notifier signalled before waiter start waiting,
     * the waiter would missed the notify signal and may wait forever.
     *
     * @see #resolveMissedSignal() resolve missed signal issue
     */
    static void missedSignal() {
        NotifyWaitCase notifyWaitCase = new NotifyWaitCase();
        Thread tiger = new Thread(() -> {
            notifyWaitCase.wait0();
            System.out.println(Thread.currentThread().getName() + " waited");
        });
        Thread goat = new Thread(() -> {
            notifyWaitCase.notify0();
            System.out.println(Thread.currentThread().getName() + " notified");
        });
        goat.start();
        tiger.start();
    }

    /**
     * @see #signalled
     */
    static void resolveMissedSignal() {
        NotifyWaitCase notifyWaitCase = new NotifyWaitCase();
        Thread zebra = new Thread(notifyWaitCase::safeWait);
        Thread glass = new Thread(notifyWaitCase::safeNotify);
        glass.start();
        zebra.start();
    }

    static void fakeNotify() {
        NotifyWaitCase notifyWaitCase = new NotifyWaitCase();
        int stock = new Random().nextInt(10);  // ignore assignment
        if (stock == 0) {
            notifyWaitCase.wait0();
            /*
             * Case in 3 threads: 2 producer and 2 consumer, stock max capacity is 2.
             *
             * producers produced and stored 2 products, then notify all
             * consumer consumed 2 products and notify all
             * producer-A was waked up and produce 1 product, then notify all
             * producer-B reacquired the locks and waked up
             * stock is 1 not 0 at this time
             * but producer-B was in the if condition and it starts to produce product
             *
             * producer-B should not be notified to produce product -- fake notify issue
             * solve: change if to while
             *
             * while condition run as spin lock and it will occupy CPU performance
             */
        }
    }

    public static void main(String[] args) {
//        threadCommunication();
//        missedSignal();
        resolveMissedSignal();
    }

}
