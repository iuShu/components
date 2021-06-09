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
     * Young Generation
     *  Serial
     *  ParNew
     *  Parallel Scavenge
     *  G1
     *
     * Tenured(Old) Generation
     *  CMS
     *  Serial Old
     *  Parallel Old
     *  G1
     */
    static void garbageCollector() {

    }

    public static void main(String[] args) {

    }

}
