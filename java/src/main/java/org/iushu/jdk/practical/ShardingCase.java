package org.iushu.jdk.practical;

/**
 * @author iuShu
 * @since 6/23/21
 */
public class ShardingCase {

    // sharding table name format: <table>_<prefix>_<index>
    static void shardingPrimaryKey() {
        long personId = 8088800;
        int rowCount = 6;   // existed rows' count, next record is 7

        long prefix = 1;
        long index = 6;     // sharding index
        long person = 0L;   // person id bits
        long next = 0L;     // table index bits

        prefix = prefix << 56;              //  8 bits 63 ~ 56
        index = index << 48;                //  8 bits 55 ~ 48
        person = personId << 16;            // 32 bits 47 ~ 16
        next = (rowCount + 1) & 0xFFFF;     // 16 bits 15 ~ 0

        long key = prefix | index | person | next;  // 64 bits
        System.out.println(key);
        System.out.println(Long.toBinaryString(key));

        // 64 bits  0000 0001 0000 0110 0000 0000 0111 1011 0110 1100 1110 0000 0000 0000 0000 0111
        // prefix   0000 0001
        // index              0000 0110
        // person                       0000 0000 0111 1011 0110 1100 1110 0000
        // next                                                                 0000 0000 0000 0111

        prefix = key >> 56;
        index = key << 8 >> 56;
        person = key << 16 >> 32;
        System.out.println(prefix);
        System.out.println(index);
        System.out.println(person);
    }

    public static void main(String[] args) {
        shardingPrimaryKey();
    }

}
