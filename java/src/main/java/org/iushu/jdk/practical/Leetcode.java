package org.iushu.jdk.practical;

import java.util.*;

/**
 * @author iuShu
 * @since 7/13/21
 */
public class Leetcode {

    static void reverseNum(int x) {
        int y = 0;
        while (x != 0) {
            if (y > 214748364 || y < -214748364) {
                y = 0;
                break;
            }
            y = y * 10 + x % 10;
            x = x / 10;
        }
        System.out.println(y);
    }

    static Map<Character, Integer> bracketsMap = new HashMap<>(6);
    static {
        bracketsMap.put('(', 1);
        bracketsMap.put(')', -1);
        bracketsMap.put('{', 2);
        bracketsMap.put('}', -2);
        bracketsMap.put('[', 3);
        bracketsMap.put(']', -3);
    }
    static boolean brackets(String raw) {
        if (raw == null || raw.isEmpty())
            return false;

        char[] chars = raw.toCharArray();
        if (chars.length % 2 != 0)
            return false;

        Stack<Character> queue = new Stack<>();
        int p, q;
        for (char c : chars) {
            q = bracketsMap.get(c);
            if (queue.size() == 0 && q < 0)
                return false;

            if (q > 0)
                queue.push(c);
            else {
                p = bracketsMap.get(queue.pop());
                if (p * -1 == q)
                    continue;
                else
                    return false;
            }
        }

        System.out.println(raw + " " + queue.isEmpty());
        return queue.isEmpty();
    }

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
        System.out.println(insertPos(new int[]{1,3,5,6}, 5));
        System.out.println(insertPos(new int[]{1,3,5,6}, 2));
        System.out.println(insertPos(new int[]{1,3,5,6}, 7));
        System.out.println(insertPos(new int[]{1,3,5,6}, 0));
    }

}
