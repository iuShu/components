package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @see java.util.concurrent.CountDownLatch one-shot phenomenon
 * @see java.util.concurrent.CyclicBarrier reusible
 *
 * @author iuShu
 * @since 4/29/21
 */
public class LatchAndBarrierCase {

    static void countDownLatchCase() {
        CountDownLatch latch = new CountDownLatch(3);
        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": running");
                Utils.sleep(new Random().nextInt(3000));

                latch.countDown();
                long remaining = latch.getCount();
                System.out.println(Thread.currentThread().getName() + ": prepared and waiting for " + remaining);

                latch.await();
                System.out.println(Thread.currentThread().getName() + ": done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable, "AAA").start();
        new Thread(runnable, "BBB").start();
        new Thread(runnable, "CCC").start();
    }

    static void cyclicBarrierCase() {
        CyclicBarrier barrier = new CyclicBarrier(3);
        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": running");
                int timed = new Random().nextInt(3000);
                Utils.sleep(timed);

                System.out.println(Thread.currentThread().getName() + ": prepared and already waited " + timed);

                int index = barrier.await();
                System.out.println(Thread.currentThread().getName() + ": done at " + index);
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable, "AAA").start();
        new Thread(runnable, "BBB").start();
        new Thread(runnable, "CCC").start();
    }

    public static void main(String[] args) {
//        countDownLatchCase();
        cyclicBarrierCase();
    }

}
