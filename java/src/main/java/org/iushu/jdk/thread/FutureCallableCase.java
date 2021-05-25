package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author iuShu
 * @since 5/25/21
 */
public class FutureCallableCase {

    /**
     * @see java.util.concurrent.FutureTask Future implementation
     * @see java.util.concurrent.FutureTask#state state of Future
     * @see java.util.concurrent.FutureTask#waiters waiters waiting on Future
     *
     * @see java.util.concurrent.FutureTask#get()
     * @see java.util.concurrent.FutureTask#awaitDone(boolean, long)
     *
     * @see java.util.concurrent.FutureTask#run()
     * @see java.util.concurrent.FutureTask#set(Object)
     * @see java.util.concurrent.FutureTask#finishCompletion()
     */
    static void futureCase() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<?> future = executorService.submit(() -> {
            Utils.sleep(2000);
            System.out.println("finished");
        });

        try {
            future.get();   // block until task is complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

}
