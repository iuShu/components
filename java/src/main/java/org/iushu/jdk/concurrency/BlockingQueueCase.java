package org.iushu.jdk.concurrency;

import org.iushu.jdk.Utils;

import java.util.concurrent.*;

/**
 * @see BlockingQueue
 *
 * @author iuShu
 * @since 5/25/21
 */
public class BlockingQueueCase {

    /**
     * @see BlockingQueue#add(Object) throws if full
     * @see BlockingQueue#offer(Object) false if full
     * @see BlockingQueue#put(Object) await if full
     * @see BlockingQueue#offer(Object, long, TimeUnit) awaitNanos if full
     *
     * @see BlockingQueue#peek() null if empty
     * @see BlockingQueue#poll() null if empty
     * @see BlockingQueue#take() await if empty
     * @see BlockingQueue#poll(long, TimeUnit) awaitNanos if empty
     * @see BlockingQueue#remove() false if empty
     */
    static void methods() {

    }

    /**
     * @see java.util.concurrent.ArrayBlockingQueue
     * @see java.util.concurrent.ArrayBlockingQueue#items Object array
     * @see java.util.concurrent.ArrayBlockingQueue#lock fair/nonfair ReentrantLock
     *
     * @see java.util.concurrent.LinkedBlockingQueue linklist implementation
     *
     * @see java.util.concurrent.SynchronousQueue like Exchanger (no capacity)
     *
     * @see java.util.concurrent.LinkedTransferQueue
     *
     * @see java.util.concurrent.PriorityBlockingQueue
     *
     * @see java.util.concurrent.DelayQueue
     */
    static void implementations() {
        Object ele = new Object();
        SynchronousQueue<Object> blockingQueue = new SynchronousQueue<>();
        blockingQueue.offer("1");
        System.out.println(blockingQueue);
        blockingQueue.offer("2");
        System.out.println(blockingQueue);
        blockingQueue.offer("3");
        System.out.println(blockingQueue);
    }

    public static void main(String[] args) {
        implementations();
    }

}
