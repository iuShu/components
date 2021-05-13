package org.iushu.dubbo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author iuShu
 * @since 5/13/21
 */
public class Utils {

    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void randomSleep(int bound) {
        sleep(new Random().nextInt(bound));
    }

}
