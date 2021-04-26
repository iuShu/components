package org.iushu.jdk.lock;

/**
 * @author iuShu
 * @since 4/25/21
 */
public class ReentrantLockCase {

    private int entrantCount = 0;
    private boolean locked = false;
    private Thread holder = null;

    synchronized void lock() {
        try {
            while (locked && holder != Thread.currentThread())
                wait();

            entrantCount++;
            locked = true;
            holder = Thread.currentThread();
            System.out.println("lock: " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void unlock() {
        if (Thread.currentThread() == holder) {
            entrantCount--;
            System.out.println("unlock: " + entrantCount);
            if (entrantCount == 0) {
                holder = null;
                locked = false;
                notify();
                System.out.println("unlock: release-" + entrantCount);
            }
        }
    }

    static void reentrantCase() {
        ReentrantLockCase lockCase = new ReentrantLockCase();
        Runnable runnable = () -> {
            lockCase.lock();
            lockCase.lock();
            lockCase.unlock();
            lockCase.unlock();
        };

        new Thread(runnable, "reentrant").start();
    }

    public static void main(String[] args) {
        reentrantCase();
    }

}
