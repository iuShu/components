package org.iushu.jdk.lock;

import org.iushu.jdk.Utils;

import java.util.Random;

/**
 * Why using  notifyAll()
 *   A read thread and write thread are both waiting, the reader is waked up by notify() but found a writeRequest
 *   the reader would keep waiting, no activated thread running in this case, everything would be stop.
 *   Another advantage of notifyAll() is to notify all read threads if no write thread requests instead of only
 *   notifying one reader execute its task.
 *
 * NOTE: NOT reentrant in this case
 *
 * @author iuShu
 * @since 4/25/21
 */
public class ReadWriteLockCase {

    private int readers = 0;
    private int writeRequest = 0;
    private boolean writing = false;
    private Thread writer = null;

    synchronized void lockRead() {
        try {
            while (writing || writeRequest != 0)
                wait();

            ++readers;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void unlockRead() {
//        if (readers > 0)
            --readers;
//        if (readers == 0)
            notifyAll();
    }

    synchronized void lockWrite() {
        try {
            ++writeRequest;  // stop subsequent read and write
            while (writing || readers != 0)
                wait();

            writing = true;
            writer = Thread.currentThread();
            --writeRequest;  // recover
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void unlockWrite() {
        if (writer != Thread.currentThread())
            throw new IllegalMonitorStateException("Caller has not held the write lock");

        writing = false;
        notifyAll();
    }

    static void readWriteLockCase() {
        ReadWriteLockCase lockCase = new ReadWriteLockCase();
        Runnable reader = () -> {
            lockCase.lockRead();
            System.out.println(Thread.currentThread().getName() + ": in reading");
            Utils.sleep(new Random().nextInt(1000));
            lockCase.unlockRead();
        };

        Runnable writer = () -> {
            lockCase.lockWrite();
            System.out.println(Thread.currentThread().getName() + ": in writing");
            Utils.sleep(new Random().nextInt(1500));
            lockCase.unlockWrite();
        };

        int scale = 50;
        while (scale-- > 0) {
            new Thread(reader, "reader-" + scale).start();
            if (System.currentTimeMillis() % 2 == 0)
                new Thread(writer, "writer-" + scale).start();
        }
    }

    public static void main(String[] args) {
        readWriteLockCase();
    }

}
