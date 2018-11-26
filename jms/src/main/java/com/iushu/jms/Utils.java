package com.iushu.jms;

/**
 * Created by iuShu on 18-10-10
 */
public class Utils {

    public static int[] getIntArray() {
        return getIntArray(0);
    }

    /**
     * python-like for each
     */
    public static int[] getIntArray(int range) {
        if (range < 1)
            range = 1;

        int[] array = new int[range];
        for (int i = 0; i < range; i++)
            array[i] = i;
        return array;
    }

}
