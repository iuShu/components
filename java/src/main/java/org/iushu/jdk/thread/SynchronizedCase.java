package org.iushu.jdk.thread;

/**
 * Be aware of the lock object holding by the synchronized method/block
 *
 * @author iuShu
 * @since 4/20/21
 */
public class SynchronizedCase {

    // class lock: SynchronizedCase.class
    static synchronized int add(int value) {
        System.out.println(Thread.currentThread().getName() + ": " + value);
        return ++value;
    }

    // class lock: SynchronizedCase.class
    static int multiple(int value) {
        synchronized (SynchronizedCase.class) {  // lock object: SynchronizedCase.class
            return value * 2;
        }
    }

    // object lock
    synchronized int deduct(int value) {
        System.out.println(Thread.currentThread().getName() + ": " + value);
        return --value;
    }

    // object lock like deduct() method
    int devide(int value) {
        synchronized (this) {   // lock object: this
            return value / 2;
        }
    }

    static void classLock() {
        int scale = 5000000;
        new Thread(() -> {
            int val = 1;
            while (true) {
                val = add(val);
                if (val >= scale)
                    break;
            }
        }, "sync-thread-1").start();

        new Thread(() -> {
            int val = 1;
            while (true) {
                val = add(val);
                if (val >= scale)
                    break;
            }
        }, "sync-thread-2").start();
    }

    static void objectLock() {
        int scale = 5000000;
        SynchronizedCase synchronizedCase = new SynchronizedCase();
        new Thread(() -> {
            int val = scale;
            while (true) {
                val = synchronizedCase.deduct(val);
                if (val <= 1)
                    break;
            }
        }, "sync-thread-1").start();

        new Thread(() -> {
            int val = scale;
            while (true) {
                val = synchronizedCase.deduct(val);
                if (val <= 1)
                    break;
            }
        }, "sync-thread-2").start();
    }

    public static void main(String[] args) {

//        classLock();
        objectLock();
    }

}
