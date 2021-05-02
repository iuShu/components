package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

import java.util.Random;

/**
 * @author iuShu
 * @since 4/22/21
 */
public class ThreadLocalCase {

    private ThreadLocal<Object> session = new ThreadLocal<>();

    private ThreadLocal<Object> initial = ThreadLocal.withInitial(() -> "initial-value");

    private ThreadLocal<Object> inheritable = new InheritableThreadLocal<>();

    static void threadLocal() {
        ThreadLocalCase threadLocalCase = new ThreadLocalCase();
        Runnable runnable = () -> {
            int scale = 20;
            Random random = new Random();
            while (true) {
                Object session = threadLocalCase.session.get();
                if (session == null)
                    threadLocalCase.session.set("session-" + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + ": " + session);

                if (--scale < 1)
                    break;
                Utils.sleep(random.nextInt(500));
            }
        };

        Thread admin = new Thread(runnable, "thread-admin");
        Thread client = new Thread(runnable, "thread-client");
        admin.start();
        client.start();
    }

    // threads would get the initial value if no thread local variable have been set before
    static void threadLocalWithInitial() {
        ThreadLocalCase threadLocalCase = new ThreadLocalCase();
        Runnable runnable = () -> {
            int scale = 20;
            Random random = new Random();
            while (true) {
                Object session = threadLocalCase.initial.get();  // first get() return initial value
                if ("initial-value".equals(session))
                    threadLocalCase.initial.set("session-" + Thread.currentThread().getName());  // set thread local value
                System.out.println(Thread.currentThread().getName() + ": " + session);

                if (--scale < 1)
                    break;
                Utils.sleep(random.nextInt(500));
            }
        };

        Thread admin = new Thread(runnable, "thread-admin");
        Thread client = new Thread(runnable, "thread-client");
        admin.start();
        client.start();
    }

    static void noInheritableThreadLocal() {
        ThreadLocalCase threadLocalCase = new ThreadLocalCase();
        threadLocalCase.session.set("session-" + Thread.currentThread().getName());
        Runnable runnable = () -> {
            int scale = 15;
            Random random = new Random();
            while (true) {
                Object session = threadLocalCase.session.get();
                if (session == null) {
                    threadLocalCase.session.set("session-" + Thread.currentThread().getName());  // set thread local value
                }
                System.out.println(Thread.currentThread().getName() + ": " + session);

                if (--scale < 1)
                    break;
                Utils.sleep(random.nextInt(500));
            }
        };
        new Thread(runnable, "child-thread").start();
        System.out.println(Thread.currentThread().getName() + ": " + threadLocalCase.session.get());
    }

    // child thread can access the thread variables of parent thread
    static void inheritableThreadLocal() {
        ThreadLocalCase threadLocalCase = new ThreadLocalCase();
        threadLocalCase.inheritable.set("session-" + Thread.currentThread().getName());
        Runnable runnable = () -> {
            int scale = 15;
            Random random = new Random();
            while (true) {
                Object session = threadLocalCase.inheritable.get();
                if (session == null)
                    threadLocalCase.inheritable.set("session-" + Thread.currentThread().getName());  // set thread local value
                System.out.println(Thread.currentThread().getName() + ": " + session);

                if (--scale < 1)
                    break;
                Utils.sleep(random.nextInt(500));
            }
        };
        new Thread(runnable, "child-thread").start();
        System.out.println(Thread.currentThread().getName() + ": " + threadLocalCase.inheritable.get());
    }

    public static void main(String[] args) {
//        threadLocal();
//        threadLocalWithInitial();
//        noInheritableThreadLocal();
        inheritableThreadLocal();
    }

}
