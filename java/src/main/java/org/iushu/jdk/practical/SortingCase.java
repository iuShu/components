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
        int len = array.length;
        for (int i = 0; i < len && changed; i++) {
            changed = false;
            for (int j = 0; j < len - i - 1; j++) {  // j < len - i - 1 (each loop can move a big num to tail)
                if (array[j] > array[j + 1]) {
                    changed = true;
                    swap(array, j, j + 1);
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
    static void selectionSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        int len = array.length, min = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i; j < len; j++) {  // find smallest in disordered
                if (array[j] < array[min] || min == 0)
                    min = j;
            }

            swap(array, i, min);
            min = 0;    // reset
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 6 4 1 8 2 5      binary divide array
     *
     * [6 4 1 8 2 5]
     * [[6 4 1] [8 2 5]]                      divide
     * [[[6 4] [1]] [[8 2] [5]]]              divide
     * [[[[6] [4]] [1]] [[[8] [2]] [5]]]      divide (shortest)
     * [[[4 6] [1]] [[2 8] [5]]]              merge
     * [[1 4 6] [2 5 8]]                      merge
     * [1 2 4 5 6 8]                          merge
     *
     * @see #divide(int[], int, int)
     * @see #merge(int[], int[])
     */
    static void mergeSort(int[] array) {
        if (array == null || array.length <= 1)
            return;
        System.arraycopy(divide(array, 0, array.length), 0, array, 0, array.length);
    }

    static int[] divide(int[] array, int left, int right) {
        int mid = (left + right) >> 1;
        if (right - left == 1)
            return new int[]{array[left]};  // length = 1

        int[] la = divide(array, left, mid);
        int[] ra = divide(array, mid, right);
        return merge(la, ra);
    }

    static int[] merge(int[] left, int[] right) {
        int[] merge = new int[left.length + right.length];
        int m = 0, l = 0, r = 0;
        while (l < left.length && r < right.length)
            merge[m++] = left[l] > right[r] ? right[r++] : left[l++];
        while (l != left.length)
            merge[m++] = left[l++];
        while (r != right.length)
            merge[m++] = right[r++];
        return merge;
    }

    /**
     * partitioned by pivot
     * left for big, right for small
     *
     * 6 4 1 8 2 5
     * pivot = 6
     * 6 4 1 8 2 5
     * l         r
     * 6 4 1 8 2 5
     *   l       r
     * 6 4 1 8 2 5
     *     l     r
     * 6 4 1 8 2 5
     *       l   r
     * 6 4 1 5 2 8
     *       l   r
     * 6 4 1 5 2 8
     *       l r
     * 6 4 1 5 2 8
     *         lr
     * 2 4 1 5 6 8
     *
     * 2 4 1 5
     * pivot = 2
     * 2 4 1 5
     * l     r
     * 2 4 1 5
     * l   r
     * 2 4 1 5
     *   l r
     * 2 1 4 5
     *   l r
     * 2 1 4 5
     *   lr
     * 1 2 4 5
     *
     */
    static void quickSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        int left = 0, right = array.length - 1;

        int partition = partition(array, left, right);
    }

    static int partition(int[] array, int left, int right) {
        // TODO complete it
        return 0;
    }

    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = new int[]{8,9,16,4,2,1,9,7, 5,12,6,8,10,8,3,2,4};

//        bubbleSort(array);
//        insertSort(array);
//        selectionSort(array);
//        mergeSort(array);

        System.out.println(Arrays.toString(array));
    }

}
