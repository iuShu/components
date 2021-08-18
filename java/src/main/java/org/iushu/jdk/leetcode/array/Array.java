package org.iushu.jdk.leetcode.array;

import java.util.*;

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
            if (array[i - 1] == array[i])
                q++;
            else
                array[i - q] = array[i];
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
        boolean right = shift <= (nums.length / 2);
        shift = right ? shift : (nums.length - shift);

        int tmp;
        for (int i = 0; i < shift; i++) {
            if (right) {
                tmp = nums[nums.length - 1];
                for (int j = nums.length - 1; j > 0; j--)
                    nums[j] = nums[j - 1];
                nums[0] = tmp;
            } else {
                tmp = nums[0];
                for (int j = 0; j < nums.length - 1; j++)
                    nums[j] = nums[j + 1];
                nums[nums.length - 1] = tmp;
            }
        }
    }

    // contain duplicate element
    static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            if (!set.add(num))
                return true;
        }
        return false;
    }

    // find single element
    static int singleNumber(int[] nums) {
        if (nums.length == 1)
            return nums[0];

        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int num : nums) {
            Integer count = map.get(num);
            if (count == null)
                map.put(num, 1);
            else
                map.remove(num);
        }
        return map.keySet().iterator().next();
    }
    static int singleNumber2(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        int r = 0;
        for (int num : nums)
            r = r ^ num;        // a^b^a = a^a^b = b^0 = b
        return r;
    }

    // intersect
    static int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null)
            return new int[0];

        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int k = 0, min = nums1.length < nums2.length ? nums1.length : nums2.length;
        int[] rs = new int[min];
        for (int i = 0, j = 0; ; ) {
            if (nums1[i] > nums2[j])
                j++;
            else if (nums1[i] < nums2[j])
                i++;
            else {
                rs[k++] = nums1[i];
                i++;
                j++;
            }

            if (i == nums1.length || j == nums2.length)
                break;
        }

        return Arrays.copyOfRange(rs, 0, k);
    }

    // add 1 on number array
    static int[] plusOne(int[] nums) {
        if (nums == null)
            return new int[]{1};

        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] + 1 <= 9) {
                nums[i] += 1;
                return nums;
            }

            nums[i] = 0;
        }

        int[] rs = new int[nums.length + 1];
        System.arraycopy(nums, 0, rs, 1, nums.length);
        rs[0] = 1;
        return rs;
    }

    // move zero elements till the right end
    static void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1)
            return;

        int hit = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                hit++;
            } else if (hit != 0) {
                nums[i - hit] = nums[i];
                nums[i] = 0;
            }
        }
    }

    // find sum of two elements equals target
    static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2)
            return new int[0];

        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++)
            map.put(nums[i], i);

        int[] rs = new int[2];
        for (int i = 0; i < nums.length; i++) {
            rs[0] = i;
            Integer index = map.get(target - nums[i]);
            if (index != null && index != i) {
                rs[1] = index;
                return rs;
            }
        }
        return new int[0];
    }

    // validate sudoku
    static boolean isValidSudoku(char[][] board) {
        Set<Character> row = new HashSet<>(9);
        Set<Character> col = new HashSet<>(9);
        Set<Character>[] grids = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0, gi; j < 9; j++) {
                if (board[i][j] != '.' && !row.add(board[i][j]))
                    return false;
                if (board[j][i] != '.' && !col.add(board[j][i]))
                    return false;

                gi = j/3 + i/3*3;
                grids[gi] = grids[gi] == null ? new HashSet<>() : grids[gi];
                if (board[i][j] != '.' && !grids[gi].add(board[i][j]))
                    return false;
            }
            row.clear();
            col.clear();
        }
        return true;
    }

    static void rotateMatrix(int[][] matrix) {
        int i = 0, j = 0, a, b, p, q, la, lb, ori, tmp, loop = 1;
        while (i < matrix.length/2) {
            int ic = matrix.length - loop;
            while (i < ic) {
                a = i;
                b = j;
                la = a;
                lb = b;
                tmp = matrix[i][j];
                do {
                    p = b;
                    q = matrix.length - a - 1;
                    ori = matrix[p][q];
                    matrix[p][q] = tmp;
                    tmp = ori;
                    a = p;
                    b = q;
                } while (!(a == la && b == lb));
                i++;
            }
            j++;
            i = j;
            loop++;
        }
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
//        int[] nums = new int[]{-1,-100,3,99};
//        shiftElement(nums, 2);
//        System.out.println("shift: " + Arrays.toString(nums));

//        int[] nums = new int[]{1,2,3,1};
//        System.out.println("duplicate: " + containsDuplicate(nums));

//        int[] nums = new int[]{2,2,1};
//        int[] nums = new int[]{4,1,2,1,2};
//        System.out.println("single: " + singleNumber(nums));
//        System.out.println("single2: " + singleNumber2(nums));

//        int[] nums1 = new int[]{4,9,5};
//        int[] nums2 = new int[]{9,4,9,8,4};
//        int[] nums1 = new int[]{1,2,2,1};
//        int[] nums2 = new int[]{2,2};
//        System.out.println("intersect: " + Arrays.toString(intersect(nums1, nums2)));

//        int[] nums = new int[]{1};
//        int[] nums = new int[]{9};
//        int[] nums = new int[]{1,9};
//        int[] nums = new int[]{9,9};
//        int[] nums = new int[]{9,3,9,9};
//        int[] nums = new int[]{4,3,2,1};
//        System.out.println("plusOne: " + Arrays.toString(plusOne(nums)));

//        int[] nums = new int[]{0,1,0};
//        int[] nums = new int[]{0,1,0,3,12};
//        moveZeroes(nums);
//        System.out.println("move zero: " + Arrays.toString(nums));

//        int[] nums = new int[]{3,3};            // 6
//        int[] nums = new int[]{3,2,4};          // 6
//        int[] nums = new int[]{0,4,3,0};          // 0
//        int[] nums = new int[]{2,7,11,15};        // 9
//        int[] nums = new int[]{20,32,40,70,150};  // 72
//        System.out.println("twoSum: " + Arrays.toString(twoSum(nums, 72)));

//        char[][] board = new char[][]{
//                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
//                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
//                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
//                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
//                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
//                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
//                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
//                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
//                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
//        System.out.println("soduku: " + isValidSudoku(board));

//        int[][] matrix = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
//        int[][] matrix = new int[][] {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
//        int[][] matrix = new int[][] {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        int[][] matrix = new int[][] {{2,29,20,26,16,28},{12,27,9,25,13,21},{32,33,32,2,28,14},{13,14,32,27,22,26},{33,1,20,7,21,7},{4,24,1,6,32,34}};
        rotateMatrix(matrix);
        System.out.println("matrix rotate:" + Arrays.deepToString(matrix));
    }

}
