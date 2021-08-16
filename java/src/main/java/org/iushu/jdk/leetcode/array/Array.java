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

    // maximize revenue
    static int maximizeRevenue(int[] prices) {
        if (prices == null || prices.length <= 1)
            return 0;

        int buy = -1, revenue = 0;
        for (int i = 0; i < prices.length; i++) {
            if (buy != -1 && prices[i] > buy)
                revenue += (prices[i] - buy);
            buy = prices[i];
        }
        return revenue;
    }

    // element shifts k position
    static void shiftElement(int[] nums, int k) {
        if (k <= 0)
            return;
        if (k % nums.length == 0 && k / nums.length != 0)
            return;

        int shift = k % nums.length;
        boolean right = shift <= (nums.length/2);
        shift = right ? shift : (nums.length - shift);

        int tmp;
        for (int i = 0; i < shift; i++) {
            if (right) {
                tmp = nums[nums.length-1];
                for (int j=nums.length-1; j>0; j--)
                    nums[j] = nums[j-1];
                nums[0] = tmp;
            }
            else {
                tmp = nums[0];
                for (int j=0; j<nums.length-1; j++)
                    nums[j] = nums[j+1];
                nums[nums.length-1] = tmp;
            }
        }
    }
    static void shiftElement2(int[] nums, int k) {
        if (k <= 0)
            return;
        if (k % nums.length == 0 && k / nums.length != 0)
            return;

    }

    public static void main(String[] args) {
        System.out.print("[Array] ");

//        int[] arr = new int[]{1,1,2};
//        int[] arr = new int[]{0,0,1,1,1,2,2,3,3,4,4,6};
//        int after = deDuplication(arr);
//        System.out.println("de-duplication: " + after + " " + Arrays.toString(arr));

//        int[] stock = new int[]{7,1,5,3,6,4};
//        int[] stock = new int[]{6,1,3,2,4,7};
//        int[] stock = new int[]{1,2,3,4,5};
//        int[] stock = new int[]{7,6,4,3,1};
//        int[] stock = new int[]{1,2};
//        int[] stock = new int[]{2,1,4};
//        System.out.println("ROI: " + maximizeRevenue(stock));

//        int[] nums = new int[]{1,2,3,4,5,6,7};
       int[] nums = new int[]{-1,-100,3,99};
        shiftElement(nums, 54944);
        System.out.println("shift: " + Arrays.toString(nums));
    }

}
