package org.iushu.redis;

import java.util.TreeMap;
import java.util.zip.CRC32;

/**
 * Partition does not perform well, even unavailable, in following cases:
 *  multiple keys operation like intersection between multiple sets.
 *  transactions involving multiple keys can not be used.
 *  data handling is complex like handle multiple RDB/AOF files and needs to aggregate the files for backup.
 *  adding and removing capacity can be complex.
 *
 * @author iuShu
 * @since 5/7/21
 */
public class PartitionCase {

    /**
     * Range By Id
     */
    static void rangePartition() {
        int redisInstanceNum = 5;

        // requires maintaining a range mapped table
        TreeMap<Integer, Integer> personRange = new TreeMap<>();
        int range = 10000;
        for (int i=0; i<redisInstanceNum; i++)
            personRange.put(i * range, i + 1);

        // mapping key
        int userId = 8410;
        Integer selected = personRange.get(personRange.floorKey(userId));
        System.out.println(userId + " selected " + selected + " redis instance");
    }

    /**
     * Consistent Hashing (Recommend)
     *
     * @see java.util.zip.CRC32
     */
    static void hashPartition() {
        int redisInstanceNum = 5;
        String key = "person:856432";
        byte[] keys = key.getBytes();

        // hashing
        CRC32 crc32 = new CRC32();
        crc32.update(keys);
        long hash = crc32.getValue();

        // modulo
        long selected = hash % redisInstanceNum;
        System.out.println(key + " selected " + selected + " redis instance");
    }

    public static void main(String[] args) {
        rangePartition();
//        hashPartition();
    }

}
