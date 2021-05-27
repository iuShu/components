package org.iushu.jdk.practical;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 5/27/21
 */
public class ArrayCase {

    // 1, 2, 2, 4, 5, 6, 6, 8, 9
    // 1, 2, 4, 5, 6, 8, 9
    static <T> T[] de_duplication(T... array) {
        if (array == null || array.length <= 1)
            return array;

        int cnt = 0, len = array.length;
        for (int i = 1; i < len; ++i) {
            if (array[i].equals(array[i - 1]))
                ++cnt;
            else
                array[i - cnt] = array[i];
            System.out.println(Arrays.toString(array));
        }
        System.out.println("duplication: " + cnt);
        return array;
    }

    // 0, 1, 2, 2, 3, 0, 4, 2
    // 2, 2, 2
    static <T> T[] de_difference(T target, T... array) {
        if (array == null || array.length <= 1)
            return array;

        int cnt = 0, len = array.length;
        for (int i = 0; i < len; i++) {
            if (target.equals(array[i])) {
                array[i] = array[i - cnt];  // unnecessary
                array[i - cnt] = target;
            }
            else
                cnt++;
            System.out.println(Arrays.toString(array));
        }
        System.out.println("target: " + (len - cnt));
        return array;
    }

    public static void main(String[] args) {
//        Integer[] arr = de_duplication(1, 2, 2, 4, 5, 6, 6, 8, 9);
        Integer[] arr = de_difference(2, 0, 1, 2, 2, 3, 0, 4, 2);
        System.out.println(Arrays.toString(arr));
    }

}
