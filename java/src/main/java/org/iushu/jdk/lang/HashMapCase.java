package org.iushu.jdk.lang;

import org.iushu.jdk.concurrency.ConcurrentHashMapCase;

import java.util.HashMap;

/**
 * @see ConcurrentHashMapCase resemblance principle
 *
 * @author iuShu
 * @since 6/4/21
 */
public class HashMapCase {

    /**
     * @see HashMap#threshold the next size value at which to resize (capacity * loadFactor)
     * @see HashMap#DEFAULT_LOAD_FACTOR 0.75f
     * @see HashMap#MIN_TREEIFY_CAPACITY
     * @see HashMap#TREEIFY_THRESHOLD
     * @see HashMap#UNTREEIFY_THRESHOLD
     *
     * @see HashMap#modCount for ConcurrentModificationException in iterator
     *
     * @see ConcurrentHashMapCase#essentialVariables() resemblance mechanism
     */
    static void essentialVariables() {

    }

    /**
     * @see ConcurrentHashMapCase#tableSize() identical mechanism
     */
    static void tableSize() {

    }

    /**
     * the hash of NULL key is 0 and will put it in 0 position
     *
     * @see HashMap#hash(Object) see below method
     * @see ConcurrentHashMapCase#hashCodeOfKey() identical mechanism
     */
    static int hashCodeOfKey() {
        String key = "insert";
        int hashcode = key.hashCode();
        return hashcode ^ hashcode >>> 16;
    }

    /**
     * calculating a position at table by the hashcode of key
     * @see ConcurrentHashMapCase#tabAt() identical mechanism
     */
    static void tabAt() {
        int hashKey = hashCodeOfKey();
        int tableSize = 9;
        int position = tableSize - 1 & hashKey;
        System.out.println(position);
    }

    /**
     * initializes or doubles table size
     * if full, allocates in accord with threshold
     * otherwise move elements with a power of two offset in the new table
     * @see HashMap#resize()
     */
    static void resize() {

    }

    /**
     * @see #hashCodeOfKey()
     * @see HashMap#putVal(int, Object, Object, boolean, boolean)
     *
     * initializes or doubles table size
     * @see HashMap#resize()
     *
     * calculating position for new node
     * @see #tabAt()
     *
     * create new node at position if no element occupied
     * @see HashMap#newNode(int, Object, Object, HashMap.Node)
     *
     * link new node to next of existed node if occupied
     * @see HashMap.Node#next
     *
     * transform to red-black tree if occupied element instance of TreeNode
     * @see HashMap.TreeNode
     * @see HashMap.TreeNode#putTreeVal(HashMap, HashMap.Node[], int, Object, Object)
     *
     * resize if new size > threshold
     * @see HashMap#resize()
     */
    static void putVal() {

    }

    /**
     * @see #hashCodeOfKey()
     * @see HashMap#getNode(int, Object)
     * @see #tabAt()
     *
     * search in linklist if found first bin element not matched
     * T = O(n)
     * @see HashMap.Node#next
     *
     * search in red-black tree if found first bin element instance of TreeNode
     * T = O(log.n)
     * @see HashMap.TreeNode
     * @see HashMap.TreeNode#getTreeNode(int, Object)
     */
    static void get() {

    }

    public static void main(String[] args) {

    }

}
