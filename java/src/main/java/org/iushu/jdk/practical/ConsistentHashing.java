package org.iushu.jdk.practical;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author iuShu
 * @since 6/22/21
 */
public class ConsistentHashing {

    private static final long HASH_FACTOR = 0x00000000100000000L;

    private int virtualScale;   // avoid resource tilting
    private TreeMap<Long, LoadBalanceNode> hashRing = new TreeMap<>();

    public ConsistentHashing() {
        this(1);    // no virtual node
    }

    public ConsistentHashing(int virtualScale) {
        this.virtualScale = virtualScale;
    }

    public void addNode(String key) {
        for (int i = 0; i < virtualScale; i++) {
            String hashKey = hashKey(key, i);
            hashRing.put(hash(hashKey), createNode(hashKey));
        }
    }

    public void removeNode(String key) {
        for (int i = 0; i < virtualScale; i++)
            hashRing.remove(hashKey(key, i));
    }

    public LoadBalanceNode selectNode(String based) {
        long hash = based.hashCode();
        Map.Entry<Long, LoadBalanceNode> entry = hashRing.ceilingEntry(hash);
        return entry != null ? entry.getValue() : hashRing.firstEntry().getValue();
    }

    public long hash(String key) {
        return key.hashCode() % HASH_FACTOR;
    }

    public String hashKey(String key, int virtual) {
        return key + "#" + virtual;
    }

    private LoadBalanceNode createNode(String key) {
        return new LoadBalanceNode(key);
    }

    class LoadBalanceNode {

        private String key;
        private int counter;

        public LoadBalanceNode(String key) {
            this.key = key;
        }

        public void access(String client) {
            counter++;
        }

        public String getKey() {
            return key;
        }

        public int getCounter() {
            return counter;
        }
    }

}
