package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

import java.util.concurrent.*;

/**
 * @author iuShu
 * @since 5/25/21
 */
public class ThreadPoolExecutorCase {

    /**
     * worker count < corePoolSize
     *  add worker with task
     * worker count >= corePoolSize
     *  workQueue not full
     *   add task to workQueue
     *  workQueue is full
     *   worker count < maximumPoolSize
     *    add worker with task
     *   worker count >= maximumPoolSize
     *    reject task
     * @see #workerAndWorkQueueCase() more details about worker and workQueue
     *
     * @see ThreadFactory#newThread(Runnable)
     * @see Executors#defaultThreadFactory()
     * @see Executors.DefaultThreadFactory
     * @see Executors.PrivilegedThreadFactory
     *
     * @see RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)
     * @see ThreadPoolExecutor#defaultHandler AbortPolicy
     * @see ThreadPoolExecutor.CallerRunsPolicy
     * @see ThreadPoolExecutor.AbortPolicy
     * @see ThreadPoolExecutor.DiscardPolicy
     * @see ThreadPoolExecutor.DiscardOldestPolicy
     */
    static void threadPool() {
        int corePoolSize = 2;
        int maximumPoolSize = 25;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory, rejectedExecutionHandler);
    }

    /**
     * @see LinkedBlockingQueue
     * @see SynchronousQueue
     * @see ScheduledThreadPoolExecutor.DelayedWorkQueue
     */
    static void executorProvided() {
        // core = maximum = 5
        // keepAliveTime = 0L
        // workQueue = LinkedBlockingQueue
        Executors.newFixedThreadPool(5);

        // core = 0, maximum = Integer.MAX_VALUE
        // keepAliveTime = 60L
        // workQueue = SynchronousQueue
        Executors.newCachedThreadPool();

        // core = maximum = 1
        // keepAliveTime = 0L
        // workQueue = LinkedBlockingQueue
        Executors.newSingleThreadExecutor();

        // core = 5, maximum = Integer.MAX_VALUE
        // keepAliveTime = 0L
        // workQueue = DelayedWorkQueue
        Executors.newScheduledThreadPool(5);
    }

    /**
     * @see ThreadPoolExecutor#workers storing Worker (HashSet)
     * @see ThreadPoolExecutor#workQueue storing Runnable task
     *
     * @see ThreadPoolExecutor#runWorker(ThreadPoolExecutor.Worker)
     * @see ThreadPoolExecutor#getTask() worker get task from the workQueue
     */
    static void workerAndWorkQueueCase() {
        Runnable task = () -> {
            while (true) {
                Utils.sleep(1500);
                System.out.println(Thread.currentThread().getName() + ": Hello");
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
//                5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2));
        for (int i = 0; i < 10; i++) {
            executorService.execute(task);
        }
    }

    public static void main(String[] args) {
        workerAndWorkQueueCase();
    }

}
