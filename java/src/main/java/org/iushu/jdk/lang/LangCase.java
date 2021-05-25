package org.iushu.jdk.lang;

import org.iushu.jdk.Utils;

import java.util.*;

/**
 * @author iuShu
 * @since 5/25/21
 */
public class LangCase {

    static void equivalent() {
        Ward ward1 = new Ward();
        Ward ward2 = new Ward();
        ward1.str = "ward";
        ward2.str = "ward";
        System.out.println(ward1 == ward2);         // false
        System.out.println(ward1.equals(ward2));    // false
        System.out.println(ward1.hashCode() == ward2.hashCode());   // true

        ward1.str = "通话";   // hashcode = 1179395
        ward2.str = "重地";   // hashcode = 1179395
        System.out.println(ward1 == ward2);         // false
        System.out.println(ward1.equals(ward2));    // false
        System.out.println(ward1.hashCode() == ward2.hashCode());   // true
    }

    static void collection() {
        Map<Integer, Object> map = new HashMap<>();
        map.put(null, 222);     // ok
        Hashtable<Integer, Object> table = new Hashtable<>();
//        table.put(null, 222);   // null pointer

    }

    public static void main(String[] args) {
//        equivalent();
        collection();
    }

    static class Ward {

        String str;

        @Override
        public int hashCode() {
            return str.hashCode();
        }

    }
}
