package org.iushu.jdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author iuShu
 * @since 4/20/21
 */
public class Utils {

    public static void sleep(long millSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(millSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long longIP(String ip) {
        if (ip == null || ip.length() < 1)
            return 0L;

        String[] segments = ip.split("\\.");
        if (segments.length != 4)
            return 0L;

        long l = 0L;
        for (int i = 0; i < 4; i++) {
            int each = Integer.valueOf(segments[i]);
            l += each << (3 - i) * 8;
        }
        return l;
    }

    public static byte[] digest(byte[] input) {
        try {
            return MessageDigest.getInstance("MD5").digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

}
