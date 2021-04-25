package org.iushu.jdk.lock;

import org.iushu.jdk.Utils;

/**
 * @see Thread.State#WAITING: release held resources and waiting
 * @see Thread.State#BLOCKED: holding resources and blocking
 * @see Thread.State#TIMED_WAITING: holding resources and sleeping
 *
 * two key object monitor's queue/set
 *  Entry-Sex: store BLOCKED threads, threads manage to contend monitor lock after lock was released.
 *  Wait-Sex: store WAITING threads, a random thread would be waked up to content with blocking threads if got notify.
 *            all waited threads would be waked up to content with blocking threads if got notifyAll.
 *
 * This lock contains thread starve issue:
 * some thread can not catch the lock and might keep waiting forever
 * use fair lock to solve the issue
 *
 * @author iuShu
 * @since 4/22/21
 */
public class SynchronizedLockCase {

    private boolean locked = false;
    private Thread holder = null;

    /**
     * other threads are blocked while the holder thread running task
     * @see Thread.State#BLOCKED
     */
    public synchronized void run() {
        System.out.println(Thread.currentThread().getName() + ": run");
        Utils.sleep(3000);
    }

    /**
     * thread that caught the synchronized lock would release held lock while it start waiting
     * other threads do also like that, threads would turn themselves into WAITING state
     * instead of BLOCK state in run(), this can optimize synchronizing in certain extent
     * @see Thread.State#WAITING
     */
    public synchronized void lock() {
        try {
            while (locked) {
                System.out.println(Thread.currentThread().getName() + ": ready wait in the method");
                wait();
            }

            locked = true;
            holder = Thread.currentThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void unlock() {
        if (holder != Thread.currentThread())
            throw new IllegalMonitorStateException("Calling thread has not held the lock");

        locked = false;
        holder = null;
        notify();
    }

    static void blockedSynchronized() {
        int scale = 15;
        SynchronizedLockCase lockCase = new SynchronizedLockCase();
        while (--scale >= 1)
            new Thread(lockCase::run, "competitor-" + scale).start();
    }

    static void waitingSynchronized() {
        int scale = 15;
        SynchronizedLockCase lockCase = new SynchronizedLockCase();
        while (--scale >= 1) {
            new Thread(() -> {
                lockCase.lock();
                System.out.println(Thread.currentThread().getName() + " get lock");
                Utils.sleep(3000);  // TIMED_WAITING
                lockCase.unlock();
            }, "competitor-" + scale).start();
        }
    }

    public static void main(String[] args) {
        blockedSynchronized();
//        waitingSynchronized();
    }

}
