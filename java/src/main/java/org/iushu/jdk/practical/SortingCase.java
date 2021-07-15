package org.iushu.jdk.practical;

import java.util.Arrays;

/**
 * bubble sort      stable          O(n^2)
 * insert sort      stable          O(n^2)
 * select sort      stable          O(n^2)
 * merge sort       stable          O(n logn)
 * quick sort       unstable        O(n logn)
 * shell sort       unstable        O(n logn)
 * heap sort        unstable
 *
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

        for (int i = 1, p; i < array.length; i++) {
            p = i;
            while (p > 0 && array[p] < array[p - 1])
                swap(array, p, --p);
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
     * binary divide and merge
     * 6 4 1 8 2 5
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

    /**
     * partitioned by pivot
     * left for big, right for small
     *
     * @see #partition(int[], int, int)
     */
    static void quickSort(int[] array) {
        if (array == null || array.length <= 1)
            return;

        int left = 0, right = array.length - 1;
        System.arraycopy(partition(array, left, right), 0, array, 0, array.length);
    }

    /**
     * Diminishing increment sort
     * an improved algorithm based on insert sort
     *
     * 6 5 1 7 2 8 4
     * step = 7/2 = 3
     * [6 7 4] [5 2] [1 8]      partitioned by step
     * [4 6 7] [2 5] [1 8]      insert sort for each
     *
     * 4 2 1 6 5 8 7            sorted
     * step = 3/2 = 1           step >= 1
     * 1 2 4 5 6 7 8            insert sort
     *
     * @see #insertSort(int[])
     */
    static void shellSort(int[] array) {
        int step = array.length >> 1;
        for (; step > 0; step >>= 1) {
            for (int i = step, p; i < array.length; i++) {  // insert sort
                p = i;
                while (p - step >= 0 && array[p - step] > array[p])
                    swap(array, p, p -= step);
            }
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * min heap
     * max heap
     * to be finished
     */
    static void heapSort(int[] array) {
        if (array == null || array.length <= 1)
            return;
        maxHeap(array, array.length);
    }

    static void maxHeap(int[] array, int limit) {
        if (limit == 1)
            return;

        int c = limit / 2 - 1, i = c, p, l, r, t = 0;
        while (i >= 0) {
            p = array[i];
            l = array[2 * i + 1];
            r = array[2 * i + 2];
            if (p >= (l > r ? l : r)) {
                i--;
                continue;
            }

            if (p < l && p < r) {
                t = 2 * i + (l > r ? 1 : 2);
            }
            else if (p < l) {    // p > r
                t = 2 * i + 1;
            }
            else if (p < r) {    // p > l
                t = 2 * i + 2;
            }
            if (t >= limit) {
                i--;
                continue;
            }
            swap(array, i, t);
            i = t > c ? --i : t;
        }

        swap(array, 0, --limit);
        maxHeap(array, limit);
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

    static int[] partition(int[] array, int left, int right) {
        if (left >= right)
            return array;

        int start = left, end = right, pivot = array[start];
        while (left != right) {
            if (pivot <= array[right])
                right--;
            else if (pivot >= array[left])
                left++;
            else
                swap(array, left, right);
        }
        array[start] = array[left];
        array[left] = pivot;
        System.out.println(Arrays.toString(array));

        partition(array, start, left - 1);
        partition(array, left + 1, end);
        return array;
    }

    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = new int[]{8,9,16,4,2,1,9,7, 5,12,6,8,10,8,3,2,4};
        System.out.println(Arrays.toString(array));

//        bubbleSort(array);
//        insertSort(array);
//        selectionSort(array);
//        mergeSort(array);
//        quickSort(array);
//        shellSort(array);
        heapSort(array);

        System.out.println(Arrays.toString(array));
    }

}
