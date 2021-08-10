package org.iushu.jdk.leetcode.array;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 8/10/21
 */
public class Array {

    // de-duplication
    static int deDuplication(int[] array) {
        if (array == null)
            return 0;
        else if (array.length <= 1)
            return array.length;

        int p = 0, q = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] == array[i])
                q++;
            else
                array[i-q] = array[i];
        }
        return array.length - q;
    }

    public static void main(String[] args) {
        System.out.print("[Array] ");

//        int[] arr = new int[]{1,1,2};
        int[] arr = new int[]{0,0,1,1,1,2,2,3,3,4,4,6};
        int after = deDuplication(arr);
        System.out.println("de-duplication: " + after + " " + Arrays.toString(arr));
    }

}
