package org.iushu.jdk.lock;

import java.util.Random;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @see ReentrantReadWriteLock
 *
 * @author iuShu
 * @since 6/8/21
 */
public class ReentrantReadWriteLockCase {

    /**
     * @see ReentrantReadWriteLock.Sync
     * @see ReentrantReadWriteLock.Sync#EXCLUSIVE_MASK
     * @see ReentrantReadWriteLock.Sync#sharedCount(int) write lock count (low 16-bits)
     * @see ReentrantReadWriteLock.Sync#exclusiveCount(int) read lock count (upper 16-bits)
     */
    static void readWriteLockCount() {
        int shared_shift = 16;
        int exclusive_mask = (1 << shared_shift) - 1;     // 2^16 - 1 = 65536 - 1

        int readState_1 = 65536;
        int writeState_1 = 1;
        int sharedCount = readState_1 >>> shared_shift;
        int exclusiveCount = writeState_1 & exclusive_mask;
        System.out.println("shared: " + sharedCount);
        System.out.println("exclusive: " + exclusiveCount);
    }

    /**
     * Fair
     * @see ReentrantReadWriteLock.FairSync
     * @see ReentrantReadWriteLock.FairSync#readerShouldBlock()
     * @see ReentrantReadWriteLock.FairSync#writerShouldBlock()
     * @see AbstractQueuedSynchronizer#hasQueuedPredecessors()
     *
     * Nonfair
     * @see ReentrantReadWriteLock.NonfairSync
     *
     * Nonfair writer thread can always barge
     * @see ReentrantReadWriteLock.NonfairSync#writerShouldBlock()
     *
     * different processing for thread starvation
     * reader should block if found writer thread waited at AQS (write priority)
     * @see ReentrantReadWriteLock.NonfairSync#readerShouldBlock()
     * @see AbstractQueuedSynchronizer#apparentlyFirstQueuedIsExclusive()
     */
    static void shouldBlock() {
    }

    /**
     * reentrant read lock
     * @see ReentrantReadWriteLock.ReadLock
     * @see ReentrantReadWriteLock.Sync#tryAcquireShared(int)
     * @see ReentrantReadWriteLock.Sync#tryReleaseShared(int)
     *
     * walkthrough:
     *  1. if write lock held by another thread, fail.
     *  2. try to grant by CASing state and update count.
     *  3. try a version with full retry loop if step 2 fails.
     * @see ReentrantReadWriteLock.Sync#fullTryAcquireShared(Thread)
     *
     * thread holding a counter for counting reentrant read times
     * @see ReentrantReadWriteLock.Sync.HoldCounter
     * @see ReentrantReadWriteLock.Sync.HoldCounter#count reentrant read(shared) counter
     * @see ReentrantReadWriteLock.Sync.ThreadLocalHoldCounter
     *
     * @see ReentrantReadWriteLock.Sync#firstReader first thread acquired the read lock (set read count from 0 to 1)
     * @see ReentrantReadWriteLock.Sync#firstReaderHoldCount the reentrant read counter of first thread
     */
    static void reentrantReadLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        try {
            System.out.println("in read lock");
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * reentrant write lock
     * @see ReentrantReadWriteLock.WriteLock
     * @see ReentrantReadWriteLock.Sync#tryAcquire(int)
     * @see ReentrantReadWriteLock.Sync#tryRelease(int)
     *
     * walkthrough:
     *  1. if read count or write count nonzero and owner isn't current, fail.
     *  2. if count would saturate, fail.
     *  acquire reentrant, set state
     *  set current as owner thread if no write thread yet
     */
    static void reentrantWriteLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        try {
            System.out.println("write lock 1");
            lock.writeLock().lock();
            try {
                System.out.println("write lock 2");
            } finally {
                lock.writeLock().unlock();
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * write lock can downgrade to read lock
     * demonstrating with a cache data object that holding with specified type
     */
    static void lockDowngrade() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        boolean cacheValid = new Random().nextBoolean();
        if (!cacheValid) {
            lock.readLock().unlock();   // release lock before acquire write lock (dead lock)
            lock.writeLock().lock();
            try {
                if (!cacheValid) {      // double-check lock
                    fetchFromDatabase();
                    cacheValid = true;
                }

                lock.readLock().lock(); // reacquire read for caching data (write lock downgrade)
            } finally {
                lock.writeLock().unlock();
            }
        }

        try {
            addCache();
        } finally {
            lock.readLock().unlock();
        }
    }

    static void fetchFromDatabase() {
        System.out.println("fetching from database");
    }
    static void addCache() {
        System.out.println("putting data to cache");
    }

    public static void main(String[] args) {
//        readWriteLockCount();
//        reentrantReadLock();
//        reentrantWriteLock();
        lockDowngrade();
    }

}
