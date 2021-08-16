package org.iushu.jdk.leetcode.search;

/**
 * @author iuShu
 * @since 8/10/21
 */
public class BinarySearch {

    // raw binary tree
    static int search(int[] nums, int target) {
        if (nums == null || nums.length < 1)
            return -1;
        if (target < nums[0] || target > nums[nums.length-1])
            return -1;
        return search0(nums, 0, nums.length-1, target);
    }
    static int search0(int[] nums, int l, int r, int t) {
        if (l == r || l + 1 == r) {
            if (nums[l] == t)
                return l;
            if (nums[r] == t)
                return r;
            return -1;
        }

        int mid = (l + r) / 2;
        if (nums[mid] == t)
            return mid;
        else if (nums[mid] > t)
            return search0(nums, 0, mid, t);
        return search0(nums, mid + 1, r, t);
    }

    // bad version search
    static int bad = 1702766719;
    static boolean isBad(int v) {
        return v >= bad;
    }
    static int firstBadVersion(int n) {
        if (n == 1)
            return isBad(n) ? 1 : -1;
        if (isBad(n) && !isBad(n-1))
            return n;
        return binSearch(0, n);
    }
    static int binSearch(int l, int r) {
        long sum = (long)l + (long)r;
        int mid = (int) (sum / 2);
        if (isBad(mid))
            return isBad(mid-1) ? binSearch(l, mid) : mid;
        return binSearch(mid, r);
    }

    // find insert position
    static int insertPos(int[] nums, int target) {
        if (nums == null || nums.length < 1 || target < nums[0])
            return 0;
        if (target > nums[nums.length-1])
            return nums.length;
        return insertPos0(nums, 0, nums.length-1, target);
    }
    static int insertPos0(int[] nums, int l, int r, int t) {
        if (l == r || l + 1 == r) {
            if (nums[l] >= t)
                return l;
            else if (nums[r] >= t)
                return r;
            else if (nums[r] < t)
                return r+1;
        }

        int mid = (l + r) / 2;
        if (nums[mid] == t)
            return mid;
        else if (nums[mid] > t)
            return insertPos0(nums, 0, mid, t);
        return insertPos0(nums, mid + 1, r, t);
    }

    public static void main(String[] args) {

    }

}
