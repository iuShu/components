package org.iushu.jdk.lang;

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

    static void packagingAndUnpackaging() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        System.out.println(c == d);             // true     Integer.IntegerCache (-128 ~ 127)
        System.out.println(e == f);             // false    compare object
        System.out.println(c == (a+b));         // true     operator cause unpackaging
        System.out.println(c.equals(a+b));      // true     operator cause unpackaging
        System.out.println(g == (a+b));         // true     operator cause unpackaging
        System.out.println(g.equals(a+b));      // false    require instance of Long
    }

    /**
     * String a = "123";
     *  put "123" into constants pool
     *  a.intern() return the address of the constant "123" in constants pool
     *
     * String b = new String("123");
     *  create object b into head, b point to the object in head
     *  put "123" into constants pool if not existed
     *  b.intern() return the address of the constant "123" in constants pool
     *
     * String c = new String("123").intern();
     *  create object c into head, c point to the object in head
     *  put "333" into constants pool if not existed
     *  intern() found "333" then return the address of constants pool
     */
    static void stringIntern() {
        String a = "123";
        String b = new String("123");

        System.out.println(a == b);                     // false
        System.out.println(a == a.intern());            // true
        System.out.println(b == b.intern());            // false
        System.out.println(a.intern() == b.intern());   // true
        System.out.println(a == b.intern());            // true

        String c = new String("333").intern();
        String d = "333";
        System.out.println(c == d);                     // true

        String e = new String("4") + new String("56");
        String f = "456";
        e = e.intern();
        System.out.println(e == f);
    }

    static void collection() {
        Map<Integer, Object> map = new HashMap<>();
        map.put(null, 222);     // ok
        Hashtable<Integer, Object> table = new Hashtable<>();
//        table.put(null, 222);   // null pointer

    }

    public static void main(String[] args) {
//        equivalent();
        packagingAndUnpackaging();
//        collection();
//        stringIntern();
    }

    static class Ward {

        String str;

        @Override
        public int hashCode() {
            return str.hashCode();
        }

    }
}
