package org.iushu.jdk.lock;

import org.iushu.jdk.Utils;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * The "mark word" is a section with 64-bits of Object Header,
 * indicates the thread lock status of object
 *
 * Depended by org.openjdk.jol module.
 * Powered by https://zhuanlan.zhihu.com/p/364080848.
 * @author iuShu
 * @since 6/4/21
 */
public class SynchronizedLockUpgrade {

    /**
     * no-lock, also known as "anonymously biased status". (non-biasable)
     *
     * biased locking startup delay:
     *  biased lock has a delay duration during JVM startup.
     *  skip delay duration by command args -XX:BiasedLockingStartupDelay=0 (default=4000)
     *
     * mark word bits in NEWLY CREATED object:
     *  25-bits not used
     *  31-bits hashcode
     *  1 bit not used
     *  4-bits gc generation mark
     *  1 bit biased lock mark
     *  2-bits lock status
     *
     * OFF  SZ   TYPE DESCRIPTION          VALUE (hex)
     * 0    8    (object header: mark)     0x0000000000000001 (non-biasable)    with startup delay
     * 0    8    (object header: mark)     0x0000000000000005 (biasable)        without delay
     *
     * non-biasable lock
     *  biased mark and lock status = 0 01    no-lock non-biasable
     * biasable lock
     *  biased mark and lock status = 1 01    no-lock biasable
     */
    static void noLock() {
        Utils utils = new Utils();
//        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(utils).toPrintable());
    }

    /**
     * mark word bits in BIASABLE status:
     *  54-bits threadId of thread that acquired lock
     *  2-bits epoch timestamp
     *  1 bit not used
     *  4-bits gc generation mark
     *  1 bit biased lock mark
     *  2-bits lock status
     *
     *  OFF  SZ   TYPE DESCRIPTION          VALUE (hex)
     *  0    8    (object header: mark)     0x0000000000000005 (biasable)                       before
     *  0    8    (object header: mark)     0x00007f576000d005 (biased: 0x0000001fd7600034)     locking
     *  0    8    (object header: mark)     0x00007f576000d005 (biased: 0x0000001fd7600034)     after
     *
     * thread id
     *  0x0000001fd7600034
     *  0000 0000 0000 0000 0000 0000 0001 1111 1101 0111 0110 0000 0000 0000 0011 0100
     *
     * locking
     *  0x00007f576000d005
     *  0000 0000 0000 0000 0111 1111 0101 0111 0110 0000 0000 0000 1101 00.00. 0.000 0.1.01
     *  0000 0000 0000 0000 0111 1111 0101 0111 0110 0000 0000 0000 1101 00                     thread id
     *                                                                      00                  epoch timestamp
     *                                                                          0               not used
     *                                                                            000 0         gc age
     *                                                                                  1       biased lock mark
     *                                                                                    01    biased lock
     *
     * after lock
     *  still saving thread id after lock release (biased to this thread)
     *
     * @see #batchRebiased() undetermined
     * @see #thinLock() biased lock upgrades to thin lock
     * @see #fatLock() biased lock upgrades to fat lock
     */
    static void biasedLock() {
        Utils utils = new Utils();

        System.out.println("before lock:");
        System.out.println(ClassLayout.parseInstance(utils).toPrintable());

        synchronized (utils) {
            long threadId = Thread.currentThread().getId();
            System.out.println("locking:");
            System.out.println("thread: " + threadId + "\t" + Long.toBinaryString(threadId));
            System.out.println(ClassLayout.parseInstance(utils).toPrintable());
        }

        System.out.println("after lock:");
        System.out.println(ClassLayout.parseInstance(utils).toPrintable());
    }

    /**
     * thin lock, also known as light-weight-lock
     *
     * lock upgrade:
     *  when a thread intends to acquire lock, lock upgrade will occurs if the thread
     *  with biased lock still in running.
     *
     * synchronized lock would also upgrades to thin lock from no-lock in following cases:
     *  1. the biased lock being closed by -XX:-UseBiasedLocking
     *  2. invoking an object's hashcode()
     *
     * principle of thin lock:
     *  1. JVM creates stack frame of thread at JVM stack
     *      1.1 stack frame [ lock record [displaced mark word] [owner] ]
     *      1.2 displaced mark word: storing original mark word of the object
     *      1.3 owner: point to the object
     *  2. copy original mark word of the object to lock record
     *  3. CAS set the object's mark word to thread's lock record
     *      3.1 CAS succeed, owner point to the object, got thin lock
     *      3.2 CAS failed, self spin for try lock, or upgrades to fat lock if spin failed
     *  4. revoke thin lock by CAS setting object's original mark word back to object's mark word
     *      4.1 CAS succeed, revoke think lock
     *      4.2 CAS failed, other thread could upgrades this lock to fat lock, block self
     *
     * spin lock
     *  spin time is 10 before 1.6
     *  added adaptive self spin after 1.6 and spin time measuring had delegated to JVM
     *
     * reentrantible
     *  create lock record every time that the thread reentrance
     *  reentrant lock record contains a NULL displaced mark word(not null at first record)
     *
     * mark word bits in THIN LOCK status:
     *  62-bits point to lock record in thread's stack frame
     *  2-bits lock status          00
     *
     * OFF  SZ   TYPE DESCRIPTION          VALUE (hex)
     * 0    8    (object header: mark)     0x0000000000000005 (biasable)                        before lock
     * 0    8    (object header: mark)     0x00007f776400d005 (biased: 0x0000001fddd90034)      main lock
     * 0    8    (object header: mark)     0x00007f774d038868 (thin lock: 0x00007f774d038868)   thread lock
     * 0    8    (object header: mark)     0x0000000000000001 (non-biasable)                    after lock
     *
     * thread lock
     *  thread found main still in running, lock upgrade to thin lock (lock contention)
     * after lock
     *  thread release thin lock and transform to non-biasable lock, straightly use thin lock for the next time
     *
     * @see #fatLock() thin lock upgrades to fat lock
     */
    static void thinLock() {
        Utils utils = new Utils();

        System.out.println("[before] " + ClassLayout.parseInstance(utils).toPrintable());

        synchronized (utils) {
            System.out.println("[main] " + ClassLayout.parseInstance(utils).toPrintable());
        }

        Thread thread = new Thread(() -> {
            synchronized (utils) {  // found main still in running, lock upgrade to thin lock
                System.out.println("[thread] " + ClassLayout.parseInstance(utils).toPrintable());
            }
        });
        thread.start();
        Utils.join(thread);

        // the thread with biased lock has terminated(no contention), rebias to current thread
//        Thread thread2 = new Thread(() -> {
//            synchronized (utils) {
//                System.out.println("[thread2] " + ClassLayout.parseInstance(utils).toPrintable());
//            }
//        });
//        thread2.start();
//        Utils.join(thread2);

        // the lock have been upgraded so the lock would never turns back to biased lock
        System.out.println("[after] " + ClassLayout.parseInstance(utils).toPrintable());    // 001 non-biasable

        // use thin lock straightly if reacquire the lock of this object
    }

    /**
     * fat lock, also known as heavy-weight-lock (monitor)
     *
     * concepts of monitor:
     *  owner: the thread owned the monitor, null during initializing or after lock release
     *  cxq: contention list, contention threads are putted in this list firstly
     *  EntryList: candidates list
     *  OnDeck: ready to contention for lock, become owner if contention passed, back to EntryList if failed
     *  WaitSet: storing threads that invoked wait() or wait(time)
     *  count:
     *  recursions: thread reentrant times
     *
     * principle of fat lock:
     *  fat lock is managed by monitor in object (monitor depended on the mutex lock in OS)
     *  1. firstly, multiple threads are entered the cxq
     *  2. cxq transfer threads to EntryList
     *  3. threads in EntryList fights for become owner
     *  4. thread on deck ready for owner contention
     *  5.1 owner decided, count + 1, owner thread starts to run
     *  5.2 other threads transfers to the WaitSet, waiting to be awakened for the next owner contention
     *  5.3 invoke owner's wait() would turns to No.6 step
     *  6. owner release, count - 1, transfer threads from WaitSet to EntryList, start next contention.
     *
     * mark word bits in THIN LOCK status:
     *  62-bits point to related monitor object
     *  2-bits lock status          10
     *
     * OFF  SZ   TYPE DESCRIPTION          VALUE (hex)
     * 0    8    (object header: mark)     0x0000000000000005 (biasable)                        before lock
     * 0    8    (object header: mark)     0x00007f47383c3005 (biased: 0x0000001fd1ce0f0c)      first lock
     * 0    8    (object header: mark)     0x00007f46f4005e4a (fat lock: 0x00007f46f4005e4a)    second lock
     * 0    8    (object header: mark)     0x00007f46f4005e4a (fat lock: 0x00007f46f4005e4a)    after lock
     *
     * second lock
     *  lock held in running thread, spin to try lock then failed, finally upgrades the thin lock to fat lock
     * after lock
     *  still saving fat lock record after revoke
     */
    static void fatLock() {
        Utils utils = new Utils();
        System.out.println("[before] " + ClassLayout.parseInstance(utils).toPrintable());

        Thread first = new Thread(() -> {
            synchronized (utils) {
                System.out.println("[first] " + ClassLayout.parseInstance(utils).toPrintable());
                Utils.sleep(2000);
            }
        }, "first");

        Thread second = new Thread(() -> {
            Utils.sleep(1000);
            synchronized (utils) {
                System.out.println("[second] " + ClassLayout.parseInstance(utils).toPrintable());
            }
        }, "second");

        first.start();
        second.start();
        Utils.join(first);
        Utils.join(second);

        System.out.println("[after] " + ClassLayout.parseInstance(utils).toPrintable());
    }

    /**
     * TODO to be determined
     * NOTE: skip biased lock delay: -XX:BiasedLockingStartupDelay=0
     *
     * JVM maintains a biased-lock-exited counter for each class (not object).
     * counter+1 as a thread revoke the biased lock,
     * JVM would executes batch rebiased if the counter reached the threshold(20).
     * the rebiased class would biased to the next thread that acquired lock.
     *
     * check JVM arguments: -XX:+PrintFlagsFinal
     * focus on following:
     *  intx BiasedLockingBulkRebiasThreshold   = 20        rebias threshold
     *  intx BiasedLockingBulkRevokeThreshold   = 40        revoke threshold
     *  intx BiasedLockingDecayTime             = 25000     reset counter duration
     *
     * OFF  SZ     TYPE DESCRIPTION          VALUE
     * 0    8      (object header: mark)     0x00007f6cda03a658 (thin lock: 0x00007f6cda03a658)     object-19
     * 0    8      (object header: mark)     0x00007f6cda03a658 (thin lock: 0x00007f6cda03a658)     object-20
     *
     * 0    8      (object header: mark)     0x0000000000000001 (non-biasable; age: 0)              object-19
     * 0    8      (object header: mark)     0x0000000000000001 (non-biasable; age: 0)              object-20
     * 0    8      (object header: mark)     0x0000000000000001 (non-biasable; age: 0)              object-29
     * 0    8      (object header: mark)     0x0000000000000001 (non-biasable; age: 0)              object-30
     */
    static Thread first, second, third;
    static void batchRebiased() {
        List<Object> objects = new ArrayList<>(40);
        for (int i = 0; i < 40; i++)
            objects.add(new Object());

        first = new Thread(() -> {
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                synchronized (object) {
                    if (i == 18 || i == 19)
                        System.out.println("[first] object-" + (i+1) + " "
                                + ClassLayout.parseInstance(object).toPrintable());
                }
            }

            LockSupport.unpark(second);
        }, "first");
        second = new Thread(() -> {
            LockSupport.park();
            for (int i = 0; i < 30; i++) {
                Object object = objects.get(i);
                synchronized (object) {
                    if (i == 18 || i == 19)
                        System.out.println("[second] object-" + (i+1) + " "
                                + ClassLayout.parseInstance(object).toPrintable());
                }
            }
        }, "second");

        first.start();
        second.start();
        Utils.join(second);

        Utils.sleep(3000);  // waiting above finished
        System.out.println("(object-19) " + ClassLayout.parseInstance(objects.get(18)).toPrintable());
        System.out.println("(object-20) " + ClassLayout.parseInstance(objects.get(19)).toPrintable());
        System.out.println("(object-29) " + ClassLayout.parseInstance(objects.get(28)).toPrintable());
        System.out.println("(object-30) " + ClassLayout.parseInstance(objects.get(29)).toPrintable());
        System.out.println("(object-31) " + ClassLayout.parseInstance(objects.get(30)).toPrintable());
    }

    /**
     * JVM arguments:
     *  intx BiasedLockingBulkRevokeThreshold   = 40        revoke threshold
     *
     * @see #batchRebiased()
     */
    static void batchRevoke() {

    }

    /**
     * invoking hashcode() on object would writes the hashcode in hashcode section of mark word
     * and the lock would becomes no-lock without biasable.
     * thread would acquires the thin lock for the next time, instead of biased lock.
     */
    static void hashcodeWithNoLock() {
        Utils utils = new Utils();
        System.out.println("[before] " + ClassLayout.parseInstance(utils).toPrintable());
        utils.hashCode();
        System.out.println("[hashcode] " + ClassLayout.parseInstance(utils).toPrintable());

        synchronized (utils) {
            System.out.println("[lock] " + ClassLayout.parseInstance(utils).toPrintable());
        }

        System.out.println("[after] " + ClassLayout.parseInstance(utils).toPrintable());
    }

    /**
     * biased lock would upgrades to fat lock if thread invoked hashcode() while holding biased lock
     */
    static void hashcodeInBiased() {
        Utils utils = new Utils();
        System.out.println("[before] " + ClassLayout.parseInstance(utils).toPrintable());

        synchronized (utils) {
            System.out.println("[lock] " + ClassLayout.parseInstance(utils).toPrintable());
            utils.hashCode();
            System.out.println("[hashcode] " + ClassLayout.parseInstance(utils).toPrintable());
        }

        System.out.println("[after] " + ClassLayout.parseInstance(utils).toPrintable());
    }

    /**
     * invoking wait() on object
     */
    static void waitImpact() {

    }

    public static void main(String[] args) {
//        noLock();
//        biasedLock();
//        thinLock();
//        fatLock();
//        batchRebiased();
        hashcodeWithNoLock();
//        hashcodeInBiased();
    }

}
