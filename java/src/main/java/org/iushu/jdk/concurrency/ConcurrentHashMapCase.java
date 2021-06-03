package org.iushu.jdk.concurrency;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Prerequisites
 *  <<< unsigned left move
 *  >>> unsigned right move
 *  ^   XOR (same-0 diff-1)
 *  Integer 32 bits
 *
 * @author iuShu
 * @since 6/3/21
 */
public class ConcurrentHashMapCase {

    /**
     * @see ConcurrentHashMap#table
     * @see ConcurrentHashMap#nextTable
     * @see ConcurrentHashMap#sizeCtl
     * @see ConcurrentHashMap#baseCount
     * @see ConcurrentHashMap#transferIndex
     *
     * @see ConcurrentHashMap#MAXIMUM_CAPACITY = Integer.MAX_VALUE / 2
     * @see ConcurrentHashMap#DEFAULT_CAPACITY = 16
     * @see ConcurrentHashMap#MIN_TREEIFY_CAPACITY transform to tree node if elements count exceed 68
     * @see ConcurrentHashMap#TREEIFY_THRESHOLD transform to tree node if exceed threshold = 8
     * @see ConcurrentHashMap#UNTREEIFY_THRESHOLD transform back to linklist if lower than threshold = 6
     */
    static void essentialVariables() {

    }

    /**
     * ConcurrentHashMap always have the capacity with power of two.
     *
     * @see ConcurrentHashMap#tableSizeFor(int)
     * @see ConcurrentHashMap#MAXIMUM_CAPACITY 1 << 30 = Integer.MAX_VALUE / 2 + 1
     */
    static void powerOfTwo() {
        int i = 9;
        int maximum = 1 << 30;
        int n = i - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n = (n < 0) ? 1 : (n >= maximum) ? maximum : n + 1;
        System.out.println(n + " > " + Integer.toBinaryString(n));
        System.out.println(maximum + " > 0" + Integer.toBinaryString(maximum));
        System.out.println(Integer.MAX_VALUE + " > 0" + Integer.toBinaryString(Integer.MAX_VALUE));
    }

    /**
     * @see ConcurrentHashMap#spread(int)
     * @see ConcurrentHashMap#HASH_BITS Integer.MAX_VALUE for calculating hashcode
     *
     * hashcode >>> 16
     *  unsigned right move 16 bits for high bits retaining
     */
    static int hashCodeOfKey() {
        String type = "batch";

        int hashBits = 0x7fffffff;
        int hashcode = type.hashCode();
        System.out.println("hashcode: " + hashcode + " > " + Integer.toBinaryString(hashcode));
        int spread = hashcode >>> 16;
        System.out.println("     >>>:      " + spread + " > 0000000000000000" + Integer.toBinaryString(spread));
        spread = hashcode ^ spread;
        System.out.println("       ^: " + spread + " > " + Integer.toBinaryString(spread));
        spread = spread & hashBits;
        System.out.println("hashBits: " + hashBits + " > 0" + Integer.toBinaryString(hashBits));
        System.out.println("       &: " + spread + " > 0" + Integer.toBinaryString(spread));
        System.out.println("---------------------- spread -------------------------");
        return spread;
    }

    /**
     * @see ConcurrentHashMap#tabAt(ConcurrentHashMap.Node[], int)
     */
    static void tabAt() {
        int hashKey = hashCodeOfKey();
        int size = 128;
        int i = size - 1;
        System.out.println(i + " > " + Integer.toBinaryString(i));
        i = i & hashKey;
        System.out.println("       &: " + i + " > " + Integer.toBinaryString(i));
        System.out.println("----------------------- tabAt -------------------------");
    }

    /**
     * transfer element nodes to new table then set to table (reselect bulk)
     * @see ConcurrentHashMap#table
     * @see ConcurrentHashMap#nextTable
     *
     * @see ConcurrentHashMap#addCount(long, int)
     * @see ConcurrentHashMap#transfer(ConcurrentHashMap.Node[], ConcurrentHashMap.Node[])
     * @see ConcurrentHashMap#helpTransfer(ConcurrentHashMap.Node[], ConcurrentHashMap.Node)
     */
    static void expandCapacity() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(1);    // capacity = 2
        map.put(882911, "Rod Johnson");
        map.put(581021, "Doug Lea");    // expand capacity
        map.put(575891, "Couch Lee");
        map.put(662014, "Henry Mock");
        map.put(378127, "Ben Penny");
    }

    /**
     * @see ConcurrentHashMap#get(Object)
     * @see ConcurrentHashMap.Node#find(int, Object) find in linklist or tree
     */
    static void getNode() {

    }

    /**
     * @see ConcurrentHashMap#putVal(Object, Object, boolean)
     * @see ConcurrentHashMap#initTable() init table if table == null
     *
     * locating element's position (rehash algorithm)
     * @see ConcurrentHashMap#tabAt(ConcurrentHashMap.Node[], int)
     * @see #tabAt()
     *
     * CAS set element to position
     * @see ConcurrentHashMap#casTabAt(ConcurrentHashMap.Node[], int, ConcurrentHashMap.Node, ConcurrentHashMap.Node)
     *
     * help transfer if table is expanding capacity
     * @see ConcurrentHashMap#helpTransfer(ConcurrentHashMap.Node[], ConcurrentHashMap.Node)
     *
     * position already contained element A, then synchronized(A) and transform the node to linklist or tree node
     * add element to A.next to make it becomes linklist
     * @see java.util.concurrent.ConcurrentHashMap.Node#next
     *
     * transform to tree node if
     *  element amount at this position > TREEIF_THRESHOLD(8) & table.length > MIN_TREEIFY_CAPACITY(64)
     * @see ConcurrentHashMap#treeifyBin(ConcurrentHashMap.Node[], int)
     * @see ConcurrentHashMap.TreeNode
     * @see ConcurrentHashMap.TreeBin indicates the red-black tree
     * @see ConcurrentHashMap.TreeBin#root
     * @see ConcurrentHashMap.TreeBin#first
     *
     * add to tree if found tree node at this position
     * @see ConcurrentHashMap.TreeBin#putTreeVal(int, Object, Object)
     *
     * finally, use a table to expanding capacity and transfering elements if found elements >= sizeCtl
     * @see ConcurrentHashMap#addCount(long, int)
     * @see ConcurrentHashMap#transfer rehash elements and transfer them to a new position at new table
     */
    static void putValue() {

    }

    public static void main(String[] args) {
//        powerOfTwo();
//        hashCodeOfKey();
//        tabAt();
        expandCapacity();
    }

}
