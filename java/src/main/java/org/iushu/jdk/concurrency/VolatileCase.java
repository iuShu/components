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

    private volatile int amount;
    private volatile int quantity;

    public void set(int value) {
        temperature = value;
    }

    public int get() {
        return temperature;
    }

    /**
     * memory barriers/fences for prevent code reordering
     *
     * volatile write
     *  add StoreStore before volatile write
     *  add StoreLoad after volatile write
     *
     * volatile read
     *  add LoadLoad after volatile read
     *  add LoadStore after volatile read
     */
    void memoryBarriers() {
        int a = amount;         // volatile read
        // LoadLoad
        // LoadStore
        int q = quantity;       // volatile read
        // LoadLoad
        // LoadStore

        int sum = a + q;        // normal write

        // StoreStore
        amount = sum + 1;       // volatile write
        // StoreLoad
        // StoreStore
        quantity = sum + 2;     // volatile write
        // StoreLoad
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
