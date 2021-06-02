package org.iushu.jdk.lang;

import org.iushu.jdk.Utils;

import javax.naming.NamingEnumeration;
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
//        collection();

        java.util.Hashtable<String, String> env = new java.util.Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

        try {
            javax.naming.directory.DirContext dirContext
                    = new javax.naming.directory.InitialDirContext(env);
            javax.naming.directory.Attributes attrs
                    = dirContext.getAttributes("m.jobcn.com", new String[] { "TXT" });
            javax.naming.directory.Attribute attr
                    = attrs.get("TXT");

            NamingEnumeration e = attr.getAll();
            while (e.hasMore()) {
                String val = e.next().toString();
                System.out.println(val);
                System.out.println(val.equals("eyJzIjowLCJ1cmwiOiJodHRwczovL3d3dy5qb2Jjbi5jb20vaW5kZXhfdXBncmFkZS5odG0ifQ=="));
            }
        } catch (javax.naming.NamingException e) {

            e.printStackTrace();
        }

    }

    static class Ward {

        String str;

        @Override
        public int hashCode() {
            return str.hashCode();
        }

    }
}
