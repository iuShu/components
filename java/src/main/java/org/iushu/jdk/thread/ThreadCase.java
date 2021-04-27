package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

/**
 * @see Thread.State#NEW new Thread()
 * @see Thread.State#TIMED_WAITING sleep()
 * @see Thread.State#WAITING wait()
 * @see Thread.State#RUNNABLE interrupt() suspend()
 * @see Thread.State#TERMINATED stop()
 *
 * @author iuShu
 * @since 4/27/21
 */
public class ThreadCase {

    private static volatile boolean reset = false;

    static void interruptCase() {
        Thread worker = new Thread(() -> {
            while (true) {
                if (!reset && Thread.currentThread().isInterrupted()) {
                    Thread.interrupted();
                    continue;
                }

                System.out.println(Thread.currentThread().getName() + ": sleeping");
                Utils.sleep(2000);
                Thread.currentThread().interrupt();
            }
        }, "worker");

        new Thread(() -> {
            while (true) {
                Thread.State state = worker.getState();
                boolean interrupted = worker.isInterrupted();
                System.out.println(String.format("worker: %s %s %s", worker.isAlive(), state, interrupted));
                if (interrupted)
                    reset = true;

                Utils.sleep(800);
            }
        }, "inspector").start();

        worker.start();
    }

    @Deprecated
    static void suspendAndResumeCase() {
        Thread worker = new Thread(() -> {
            while (true) {
                Utils.sleep(2000);
                Thread.currentThread().suspend();
                System.out.println(">> " + Thread.currentThread().getState());
            }
        }, "worker");

        new Thread(() -> {
            int scale = 20;
            while (true) {
                System.out.println(String.format("worker: %s %s %s %s",
                        worker.isAlive(), worker.getState(), worker.isInterrupted(), System.currentTimeMillis()));
                Utils.sleep(500);
                if (scale-- == 0)
                    worker.resume();
                if (scale == -2)
                    worker.stop();
            }
        }, "inspector").start();

//        worker.start();
    }

    public static void main(String[] args) {
//        interruptCase();
        suspendAndResumeCase();
    }

}
