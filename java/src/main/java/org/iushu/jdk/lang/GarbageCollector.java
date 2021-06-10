package org.iushu.jdk.lang;

import org.iushu.jdk.Utils;

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
     *  example: [ Eden Space | Sur1 | Sur2 ]   copy survivor to Sur2 then reclaim other areas
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
     * Serial Old
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
     *  designed to shorten stop time of reclaim
     *  running steps:
     *      1. initial mark         mark object linked with root (stop the world)
     *      2. concurrent mark      search from object linked to root
     *      3. remark               rectify/complement mark (stop the world)
     *      4. concurrent sweep     reclaim marked object
     *  defect:
     *      sensitive about the CPU core's quantity (i-CMS deprecated)
     *      reserve extra space for floating garbage that generated between mark and sweep
     *      memory fragmentation caused by mark-sweep algorithm
     *
     * G1 (Garbage First)
     *  the fully-featured garbage collector, designed to replace CMS
     *  can built the Pause Prediction Model and reclaim on CollectionSet (not generations)
     *  Mixed GC mode means G1 only focus on memory region with the most garbage
     *  collect on region with the most garbage firstly (Garbage First)
     *  as a whole is Mark-Compact, but using Mark-Copy between regions
     *  default expected gc time 200 ms (garbage slowly piled up if time too short)
     *  running steps:
     *      1. initial mark         mark object linked to root in Minor GC (stop the world)
     *      2. concurrent mark      reachability analysis from root
     *      3. final mark           handle change during concurrent mark (stop the world)
     *      4. live data counting and evacuation    updating region statistics and sorting then reclaim (stop the world)
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
     */
    static void generationSpaceAllocate() {
        System.out.println("total: " + Runtime.getRuntime().totalMemory());
        System.out.println("  max: " + Runtime.getRuntime().maxMemory());
        System.out.println(" free: " + Runtime.getRuntime().freeMemory());

        int _1MB = 1024 * 1024;
        byte[] all1, all2, all3, all4;
        all1 = new byte[2 * _1MB];
        all2 = new byte[2 * _1MB];
        all3 = new byte[2 * _1MB];
        all4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        generationSpaceAllocate();
    }

}
