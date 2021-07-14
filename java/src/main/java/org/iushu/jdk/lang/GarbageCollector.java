package org.iushu.jdk.lang;

/**
 * @author iuShu
 * @since 6/9/21
 */
public class GarbageCollector {

    /**
     * Reference Counting GC
     *
     * Tracing GC
     */
    static void collectorType() {

    }

    /**
     * Partial GC
     *  Minor/Young GC      young generation
     *  Major/Old GC        old generation
     *  Mixed GC            young and partial old generation
     *  Full GC             Heap and Method Area
     */
    static void reclaimType() {

    }

    /**
     * Weak Generational Hypothesis
     *  Most of object became useless soon
     * Strong Generational Hypothesis
     *  the more times an object is collected by GC, the harder it is to reclaim it
     * Inter-generational Reference Hypothesis
     *  inter-generational reference are very rare compared to same-generational reference
     *
     * GC Root
     *  powered by OopMap
     *
     * Safe Point
     *  Preemptive Suspension   stop all, awake to run if not reached safe point
     *  Voluntary Suspension    stop if the gc flag active (round robin to check)
     * Safe Region
     *  safe point expanded for threads that are blocked/sleep
     *  only available to exit region after gc finished
     */
    static void generationalHypothesis() {

    }

    /**
     * Mark-Sweep Algorithm
     *  a simple reclaim algorithm with mark and sweep two steps
     *  defect: memory fragmentation
     *
     * Mark-Copy Algorithm
     *  split memory to areas, mark survivors and copy to an area then reclaim other areas
     *  defect: memory shrink
     *  example: [ Eden Space | Sur0 | Sur1 ]   copy survivor to Sur1 then reclaim other areas
     *
     * Mark-Compact Algorithm
     *  mark and moving survivors together
     *  defect: object moving overhead
     *
     * Mixed Algorithm
     *  mark and sweep object, apply mark-compact if too much memory fragments
     */
    static void reclaimAlgorithm() {

    }

    /**
     *  Young Gen               Tenured(Old) Gen
     * ------------------------------------------
     *  Serial                  Serial Old
     *  ParNew
     *  Parallel Scavenge       Parallel Old
     *  G1                      G1
     *  CMS                     CMS
     * ------------------------------------------
     *  Mark-Copy               Mark-Compact
     *
     * Serial
     *  a simple and efficiency single thread garbage collector
     *  stop all client threads for reclaim (stop the world)
     *
     * ParNew
     *  the multi-threaded version of Serial garbage collector
     *  reclaim algorithm runs on multiple threads whereas the Serial runs on single
     *  preferred garbage collector after 1.7 (work with CMS)
     *  abolish this collector after 1.9 (merge into CMS)
     *  -XX:ParallelGCThreads       controlling gc thread's count
     *
     * Parallel Scavenge
     *  a multi-threaded Mark-Copy garbage collector
     *  focus on Throughput, throughput = program time / (program time + gc time)
     *  an adaptable garbage collector, turn on by -XX:UseAdaptiveSizePolicy
     *  -XX:GCTimeRatio             reclaim time allowed for GC = 1 / (1 + val) limited from 0 to 100
     *  -XX:MaxGCPauseMillis        try to assure gc time not less than value millis
     *  -XX:UseAdaptiveSizePolicy   delegate collector to control dynamically on generations' size and reclaim ratio
     *
     * Serial Old (Parallel Scavenge Mark-Sweep)
     *  a single thread Mark-Compact garbage collector
     *  stop all client threads for reclaim, like Serial
     *  work with Parallel Scavenge before 1.5
     *  the backup collector of CMS
     *
     * Parallel Old
     *  the multi-threaded version of Parallel Scavenge, Mark-Compact
     *  work with Parallel Scavenge can be a good choice
     *
     * CMS (Concurrent Mark-Sweep)
     *  designed to shorten stop time of reclaim for Old generation
     *  running steps:
     *      1. initial mark         mark object linked with root (stop the world)
     *      2. concurrent mark      search from object linked to root
     *      3. remark               rectify/complement mark (stop the world)
     *      4. concurrent sweep     reclaim marked object
     *  defect:
     *      sensitive about the CPU core's quantity (i-CMS deprecated)
     *      requires extra space reserved for floating garbage that generated between mark and sweep
     *      memory fragmentation caused by Mark-Sweep algorithm
     *
     * G1 (Garbage First)
     *  the fully-featured garbage collector, designed to replace CMS
     *  can built the Pause Prediction Model and reclaim on CollectionSet (not generations)
     *  Mixed GC mode means G1 only focus on memory region with the most garbage
     *  collect on region with the most garbage firstly (Garbage First)
     *  as a whole is Mark-Compact, but using Mark-Copy between regions
     *  default expected gc time 200 ms (garbage slowly piled up if time too short)
     *  running steps:
     *      1. initial mark         scanning and analyze object for RSet/CSet, accompany a young gc (stop the world)
     *      2. region scanning      scanning the object referenced the root
     *      3. concurrent mark      scan throughout the heap (can be interrupted by young gc)
     *      4. remark               rectify/complement mark, reclaim completely vacant region (stop the world)
     *      5. cleanup              clean marked region (Young & Old)
     *
     * NOTE: All the garbage collectors are designed to keep up with the Allocating Rate of client application
     *
     * Shenandoah
     *  a low-latency garbage collector designed by RedHat
     *  reclaim phase can run with client thread in parallel whereas G1 can not
     *  running in 9 steps ...
     *
     * ZGC (Z Garbage Collector)
     *  an experimental low-latency garbage collector designed by Oracle and published after 1.11
     *  highly resemblance with Shenandoah but ZGC could be the next SUPER STAR
     *  divide memory to small(2M), medium(32M) and large Region(unfixed)
     *
     * Epsilon
     *  a No-op garbage collector designed by RedHat
     *  for application running in short time with enough memory space
     */
    static void garbageCollector() {

    }

    /**
     * set before run:
     *  -Xms20M -Xmx20M -Xmn10M
     *  -XX:Survivor-Ratio=8
     *  -XX:+PrintGCDetails
     *
     * Young Gen Space = Eden size + survivor-0 size
     * big object would be allocated at Tenured(Old) Gen directly if their size larger than -XX:PretenureSizeThreshold
     *
     * Upgrade to Tenure(Old) Generation
     *  object would be moved to survivor-0 and set age to 1 if survive from the first Minor GC
     *  every time the object survive from a Minor GC, the age increase one
     *  upgrade generation and move object into Tenure Gen if age exceed the threshold(default 15)
     *  the threshold can set by -XX:MaxTenuringThreshold=..
     *
     *  object also would be upgraded generation if the space size used by certain objects which has the same age
     *  exceed the half of total size in survivor space
     *
     *  object in Eden Gen would be moved to Tenure Gen directly if Survivor space insufficient
     *
     *  Tenure Gen will perform a Minor GC if space large than Eden total space or historical upgrade required space
     *  otherwise perform a Full GC
     */
    static void generationSpaceAllocate() {
        int _1MB = 1024 * 1024;
        byte[] all1, all2, all3, all4;
        all1 = new byte[2 * _1MB];
        all2 = new byte[2 * _1MB];
        all3 = new byte[2 * _1MB];
        all4 = new byte[4 * _1MB];

//        System.out.println("total: " + Runtime.getRuntime().totalMemory());
//        System.out.println("  max: " + Runtime.getRuntime().maxMemory());
//        System.out.println(" free: " + Runtime.getRuntime().freeMemory());
    }

    public static void main(String[] args) {
        generationSpaceAllocate();
    }

}
