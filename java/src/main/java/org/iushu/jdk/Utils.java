package org.iushu.jdk;

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

}
