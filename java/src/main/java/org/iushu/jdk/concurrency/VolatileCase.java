package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

/**
 * Concurrency feature: Atomicity Visibility Orderliness
 *
 * Volatile assurance: Visibility Orderliness
 * Synchronized assurance: Atomicity Orderliness
 * Lock assurance: Atomicity Visibility Orderliness
 *
 * @author iuShu
 * @since 4/27/21
 */
public class VolatileCase {

    private volatile int temperature;

    public void set(int value) {
        temperature = value;
    }

    public int get() {
        return temperature;
    }

    static void readWriteCase() {
        VolatileCase volatileCase = new VolatileCase();
        Runnable setter = () -> {
            int seed = 1;
            while (seed <= 100) {
                volatileCase.set(seed++);
                Utils.sleep(50);
            }
        };
        Runnable getter = () -> {
            while (true)
                System.out.println(Thread.currentThread().getName() + ": " + volatileCase.get());
        };

        int scale = 10;
        while (scale-- >= 1)
            new Thread(getter, "getter-" + scale).start();
        new Thread(setter, "setter").start();
    }

    public static void main(String[] args) {
        readWriteCase();
    }

}
