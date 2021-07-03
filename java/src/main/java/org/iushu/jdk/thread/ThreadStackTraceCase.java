package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;
import org.iushu.jdk.lang.JavaMonitorTools;

/**
 * a simple and crude Thread monitoring tool
 * @see JavaMonitorTools#jstack() more professional tool
 *
 * @author iuShu
 * @since 6/11/21
 */
public class ThreadStackTraceCase {

    /**
     * @see Thread#getAllStackTraces()
     */
    static void stackTrace() {
        int scale = 10;
        for (int i = 0; i < scale; i++) {
            new Thread(() -> {
                Utils.sleep(100000);
            }, "trace-" + (i + 1)).start();
        }

        Utils.sleep(3000);
        new Thread(() -> {
            Thread.getAllStackTraces().forEach((thread, stackTraces) -> {
                if (thread == Thread.currentThread())
                    return;
                System.out.println(thread.getName() + " with stack: " + stackTraces.length);
                for (StackTraceElement stackTrace : stackTraces)
                    System.out.println(stackTrace);
                System.out.println();
            });
        }, "inspector").start();
    }

    public static void main(String[] args) {
        stackTrace();
    }

}
