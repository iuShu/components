package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

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
            Utils.sleep(2000);
            notify();
        }
    }

    void notify1() {
        synchronized (SynchronizedCase.class) {  // static monitor lock
            Utils.sleep(2000);
            notify();
        }
    }

    private Object lock1 = new Object();
    private Object lock2 = lock1;

    void referenceLock1() {
        synchronized (lock1) {
            System.out.println("lock1 start");
            Utils.sleep(2000);
            System.out.println("lock1 end");
        }
    }

    void referenceLock2() {
        synchronized (lock2) {
            System.out.println("lock2 start");
            Utils.sleep(2000);
            System.out.println("lock2 end");
        }
    }

    private InnerClass innerClass = new InnerClass();

    void notifyInner() {
        synchronized (innerClass) {
            Utils.sleep(2000);
            innerClass.notify();
        }
    }

    class InnerClass {

        synchronized void wait0() {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void notifyOuter() {
            synchronized (SynchronizedCase.this) {
                Utils.sleep(2000);
                SynchronizedCase.this.notify();
            }
        }

    }

    /**
     * @see #wait0()
     * @see #notify0()
     */
    static void classObjectMonitor() {
        SynchronizedCase synchronizedCase = new SynchronizedCase();
        new Thread(synchronizedCase::wait0, "waiter").start();

//        new Thread(synchronizedCase::notify0, "notifier").start();
        new Thread(synchronizedCase::notify1, "notifier").start();
    }

    /**
     * @see #wait0()
     * @see InnerClass#notifyOuter()
     *
     * @see InnerClass#wait0()
     * @see #notifyInner()
     */
    static void outerAndInnerCase() {
        SynchronizedCase synchronizedCase = new SynchronizedCase();

//        new Thread(synchronizedCase::wait0, "waiter").start();
//        new Thread(synchronizedCase.innerClass::notifyOuter, "notifier").start();

        new Thread(synchronizedCase.innerClass::wait0, "waiter").start();
        new Thread(synchronizedCase::notifyInner, "notifier").start();
    }

    static void referenceLockCase() {
        SynchronizedCase synchronizedCase = new SynchronizedCase();
        new Thread(synchronizedCase::referenceLock1, "lock-1").start();
        new Thread(synchronizedCase::referenceLock2, "lock-2").start();
    }

    public static void main(String[] args) {
//        classObjectMonitor();
//        outerAndInnerCase();
        referenceLockCase();
    }

}
