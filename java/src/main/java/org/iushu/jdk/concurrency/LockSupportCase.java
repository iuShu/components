package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

import java.util.concurrent.locks.LockSupport;

/**
 * @author iuShu
 * @since 4/29/21
 */
public class LockSupportCase {

    /**
     * @see LockSupport#park thread state turns to WAITING
     * @see LockSupport#parkNanos thread state turns to TIMED_WAITING
     */
    static void simpleCase() {
        Object blocker = new Object();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + ": start");
            LockSupport.park(blocker);
//            LockSupport.parkNanos(blocker, SECONDS.toNanos(2));
            System.out.println(Thread.currentThread().getName() + ": end");
        };
        Thread worker = new Thread(runnable, "worker");
        Thread runner = new Thread(runnable, "runner");
        new Thread(() -> {
            while (true) {
                System.out.println(String.format("\n%s: %s %s", worker.getName(), worker.getState(), worker.isInterrupted()));
                System.out.println(String.format("%s: %s %s\n", runner.getName(), runner.getState(), runner.isInterrupted()));
                if (!worker.isAlive() && !runner.isAlive())
                    break;
                Utils.sleep(1000);
            }
        }, "inspector").start();
        worker.start();
        runner.start();
    }

    public static void main(String[] args) {
        simpleCase();
    }

}
