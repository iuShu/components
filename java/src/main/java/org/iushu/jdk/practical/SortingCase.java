package org.iushu.jdk.practical;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 5/27/21
 */
public class SortingCase {

    static void bubbleSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        boolean changed = true;
        int len = array.length, temp;
        for (int i = 0; i < len && changed; i++) {
            changed = false;
            for (int j = 0; j < len - i - 1; j++) {  // j < len - i - 1 (each loop can move a big num to tail)
                if (array[j] > array[j + 1]) {
                    changed = true;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 6 4 1 7 5
     *
     * 6 | 4 1 7 5      ordered | disordered
     * 4 6 | 1 7 5      select a disordered element and insert in proper place of ordered area
     * 1 4 6 | 7 5
     */
    static void insertSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        int len = array.length, temp, p;
        for (int i = 1; i < len; i++) {
            p = i;
            temp = array[i];
            while (p >= 1 && temp < array[p-1])   // find insert place (backward)
                array[p] = array[--p];
            array[p] = temp;
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 6 5 1 7 2
     *
     * | 6 5 1 7 2      ordered | disordered
     * 1 | 5 6 7 2      select the smallest element of disordered area and swap them
     * 1 2 | 6 7 5
     */
    static void selectSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        int len = array.length, min = 0, temp;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i; j < len; j++) { // find smallest in disordered
                if (array[j] < array[min] || min == 0)
                    min = j;
            }

            temp = array[i];
            array[i] = array[min];
            array[min] = temp;
            min = 0;    // reset
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 6 4 1 8 2 5      binary divide array
     *
     * [6 4 1] [8 2 5]           shorter array
     * [[6] [4 1]] [[8] [2 5]]   shortest array
     * [6 8]
     */
    static void mergeSort(int[] array) {

    }

    /**
     * divide
     */
    static void quickSort(int[] array) {

    }

    public static void main(String[] args) {
        int[] array = new int[]{8,9,16,4,2,1,9,7,5,12,6,8,10,8,3,2,4};

//        bubbleSort(array);
//        insertSort(array);
        selectSort(array);

        System.out.println(Arrays.toString(array));
    }

}
