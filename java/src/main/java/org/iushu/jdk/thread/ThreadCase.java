package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

import java.util.Random;

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

    /**
     * thread in BLOCKED state make no reaction with interrupt()
     * @see Thread.State#BLOCKED
     */
    static void blockInterruptCase() {
        Object monitor = new Object();
        Thread worker = new Thread(() -> {
            synchronized (monitor) {
                System.out.println("worker: got monitor");
                Utils.sleep(1000);
                System.out.println("worker: end");
            }
        });
        Thread runner = new Thread(() -> {
            synchronized (monitor) {            // make no reaction with interrupt
                System.out.println("runner: got monitor");
                Utils.sleep(1000);  // reacted to interruption (throw exception and clear flag)
                System.out.println("runner: end");
            }
        });
        worker.start();
        runner.start();

        Utils.sleep(200);
        System.out.println("runner state: " + runner.getState());
        if (runner.getState() == Thread.State.BLOCKED)
            runner.interrupt();
        System.out.println("main: end");
    }

    /**
     * Dead-lock thread are BLOCKED so it make no reaction with interrupt()
     * @see #blockInterruptCase()
     */
    static void dealLockInterruptCase() {
        Object bread = new Object();
        Object milk = new Object();
        Thread worker = new Thread(() -> {
            synchronized (bread) {
                System.out.println("worker: got bread");
                Utils.sleep(500);
                synchronized (milk) {
                    System.out.println("worker: got milk");
                }
            }
        });
        Thread runner = new Thread(() -> {
            synchronized (milk) {
                System.out.println("runner: got milk");
                Utils.sleep(500);
                synchronized (bread) {
                    System.out.println("runner: got bread");
                }
            }
        });
        worker.start();
        runner.start();

        Utils.sleep(1000);
        System.out.println(String.format("main: worker-%s runner-%s", worker.getState(), runner.getState()));
        Thread t = new Random().nextInt(2) == 0 ? worker : runner;
        t.interrupt();  // can not interrupt deadlock thread (deadlock threads are BLOCKED)
        System.out.println("main: end");
    }

    static void sleepInterruptCase() {
        Thread worker = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) // interrupted flag are cleared by sleep()
                    break;

                System.out.println("sleep start");

                try {
                    Thread.sleep(1000);             // reacted to interruption and clear flag
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();     // remark the flag to stop while-loop
                    e.printStackTrace();
                }

                System.out.println("sleep end");
            }
        });
        worker.start();

        Utils.sleep(300);
        if (worker.getState() == Thread.State.TIMED_WAITING)
            worker.interrupt();
        System.out.println("main: end");
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
//        suspendAndResumeCase();
//        dealLockInterruptCase();
//        blockInterruptCase();
        sleepInterruptCase();
    }

}
