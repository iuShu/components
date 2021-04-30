package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

import java.util.concurrent.Semaphore;

/**
 * @author iuShu
 * @since 4/29/21
 */
public class SemaphoreCase {

    static void simpleCase() {
        Semaphore semaphore = new Semaphore(1);
        Runnable runnable = () -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ": acquired semaphore");
                Utils.sleep(5000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread warriors = new Thread(runnable, "warriors");
        Thread soldiers = new Thread(runnable, "soldiers");
        new Thread(() -> {
            while (true) {
                System.out.println(String.format("\n%s: %s %s", warriors.getName(), warriors.getState(), warriors.isInterrupted()));
                System.out.println(String.format("%s: %s %s\n", soldiers.getName(), soldiers.getState(), soldiers.isInterrupted()));
                Utils.sleep(200);
                if (!warriors.isAlive() && !soldiers.isAlive())
                    break;
            }
        }, "inspector").start();
        warriors.start();
        soldiers.start();
    }

    public static void main(String[] args) {
        simpleCase();
    }

}
