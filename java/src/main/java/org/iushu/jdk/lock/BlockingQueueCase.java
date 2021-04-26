package org.iushu.jdk.lock;

import org.iushu.jdk.Utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Blocking queue: BLOCKING if no elements or the queue is full
 * Normal queue: return NULL if no elements and reject adding element if full
 *
 * @author iuShu
 * @since 4/25/21
 */
public class BlockingQueueCase {

    private Queue queue = new LinkedList();
    private int limit = 7;

    public BlockingQueueCase() {}

    public BlockingQueueCase(int limit) {
        this.limit = limit;
    }

    synchronized void enqueue(Object element) {
        try {
            while (queue.size() == limit)
                wait();

            if (queue.size() == 0)
                notifyAll();   // notify dequeue thread
            queue.offer(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized Object dequeue() {
        try {
            while (queue.size() == 0)
                wait();

            if (queue.size() == limit)
                notifyAll();   // notify enqueue thread
            return queue.poll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void simpleBlockingQueueCase() {
        BlockingQueueCase blockingQueueCase = new BlockingQueueCase(3);
        Runnable provider = () -> {
            Utils.sleep(new Random().nextInt(1000));
            System.out.println(">> " + Thread.currentThread().getName());
            blockingQueueCase.enqueue(Thread.currentThread().getName());
        };
        Runnable consumer = () -> {
            Utils.sleep(new Random().nextInt(1000));
            Object element = blockingQueueCase.dequeue();
            System.out.println("<< " + element);
        };

        int scale = 100;
        while (scale-- > 0) {
            new Thread(provider, "provider-" + scale).start();
            new Thread(consumer, "consumer-" + scale).start();
            new Thread(provider, "provider0-" + scale).start();
            new Thread(consumer, "consumer0-" + scale).start();
        }
    }

    public static void main(String[] args) {
        simpleBlockingQueueCase();
    }

}
