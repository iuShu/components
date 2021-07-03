package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

import java.util.concurrent.*;

/**
 * @author iuShu
 * @since 7/2/21
 */
public class ForkJoinPoolCase {

    static void gettingStarted() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(() -> {
            System.out.println("[begin] Callable at " + Thread.currentThread().getName());
            Utils.sleep(1000);
            System.out.println("[end]");
            return "RRR";
        });

        String r = task.get();
        System.out.println("return: " + r);
    }

    /**
     * submit task
     * @see ForkJoinPool#submit(Callable)
     * @see ForkJoinTask.AdaptedCallable wrap Callable task as ForkJoinTask
     * @see ForkJoinPool#externalPush(ForkJoinTask)
     * @see ForkJoinPool#externalSubmit(ForkJoinTask) core method
     * @see ForkJoinPool#workQueues = new WorkQueue[n]                  initialize array
     * @see ForkJoinPool.WorkQueue workQueues[k] = new WorkQueue(..)    add WorkQueue
     * @see ForkJoinPool.WorkQueue#array add task into array
     * @see ForkJoinPool#signalWork
     * @see ForkJoinPool#tryAddWorker(long)
     * @see ForkJoinPool#createWorker()
     * @see ForkJoinPool.ForkJoinWorkerThreadFactory#newThread(ForkJoinPool)
     * @see ForkJoinWorkerThread#start()
     * @see ForkJoinWorkerThread#run()
     * @see ForkJoinPool#runWorker(ForkJoinPool.WorkQueue)
     * @see ForkJoinPool#scan(ForkJoinPool.WorkQueue, int)
     * @see ForkJoinPool.WorkQueue#runTask(ForkJoinTask)
     * @see ForkJoinTask#doExec()
     * @see ForkJoinTask.AdaptedCallable#exec()
     * @see Callable#call()
     */
    static void initialize() {

    }

    static class FibonacciTask extends RecursiveTask<Integer> {

        int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1)
                return n;

            System.out.println(Thread.currentThread().getName());
            FibonacciTask f1 = new FibonacciTask(n - 1);
            FibonacciTask f2 = new FibonacciTask(n - 2);
            f1.fork();
            return f2.compute() + f1.join();
        }
    }

    static void forkJoinCase() {
        int scale = 10;
        FibonacciTask fibonacciTask = new FibonacciTask(scale);
        ForkJoinPool pool = new ForkJoinPool();
        Integer r = pool.invoke(fibonacciTask);
        System.out.println(r);
    }

    public static void main(String[] args) throws Exception {
//        gettingStarted();
        forkJoinCase();
    }

}
