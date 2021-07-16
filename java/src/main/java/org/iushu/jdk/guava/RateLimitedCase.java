package org.iushu.jdk.guava;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import org.iushu.jdk.Utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author iuShu
 * @since 7/15/21
 */
public class RateLimitedCase {

    static void prerequisite() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Utils.sleep(300);
        long elapsed = stopwatch.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(elapsed / 1000 + " ms");
    }

    static void gettingStarted() {
        int scale = 30;
        double permitsPerSec = 1;
        CountDownLatch latch = new CountDownLatch(scale);
        RateLimiter rateLimiter = RateLimiter.create(permitsPerSec);  // why double type
        Runnable task = () -> {
            try {
                latch.countDown();
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            rateLimiter.acquire();
            System.out.println(Thread.currentThread().getName() + " run");
        };

        for (int i = 0; i < scale; i++) {
            new Thread(task, "t-" + i).start();
        }
    }

    /**
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#maxPermits
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#storedPermits
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#maxBurstSeconds
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#stableIntervalMicros   500ms in 2 QPS
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#nextFreeTicketMicros
     * @see com.google.common.util.concurrent.SmoothRateLimiter.SmoothBursty#mutexDoNotUseDirectly  mutex used at acquire permit
     */
    static void coreParameters() {

    }

    public static void main(String[] args) {
//        prerequisite();
        gettingStarted();
    }

}
