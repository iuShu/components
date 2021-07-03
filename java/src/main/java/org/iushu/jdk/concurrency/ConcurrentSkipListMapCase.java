package org.iushu.jdk.concurrency;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author iuShu
 * @since 6/7/21
 */
public class ConcurrentSkipListMapCase {

    static void simpleCase() {
        ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put(111, "aaa");
        skipListMap.put(222, "bbb");
        skipListMap.put(333, "ccc");
        skipListMap.put(444, "ddd");
        skipListMap.put(555, "eee");
        skipListMap.put(666, "fff");
        skipListMap.put(777, "ggg");
    }

    public static void main(String[] args) {
        simpleCase();
    }

}
