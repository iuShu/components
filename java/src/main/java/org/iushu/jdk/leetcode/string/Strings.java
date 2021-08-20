package org.iushu.jdk.leetcode.string;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 8/18/21
 */
public class Strings {

    static void reverse(char[] s) {
        if (s == null || s.length < 2)
            return;

//        char tmp;
        for (int i = 0; i < s.length/2; i++) {
//            tmp = s[s.length - i - 1];
//            s[s.length - i - 1] = s[i];
//            s[i] = tmp;

            // xor (more efficient)
            s[s.length - i - 1] ^= s[i];
            s[i] ^= s[s.length - i - 1];
            s[s.length - i - 1] ^= s[i];
        }
    }

    static int reverse(int x) {
        if (x > -9 && x < 9)
            return x;
        if (x == Integer.MIN_VALUE)
            return 0;

        boolean negative = x < 0;
        x = x < 0 ? x * -1 : x;
        int rs = 0, tmp = 0;
        while (x != 0) {
            rs = rs == 0 ? 0 : rs * 10;
            rs += (x % 10);
            x /= 10;

            if (rs / 10 != tmp)
                return 0;
            tmp = rs;
        }
        return negative ? rs * -1 : rs;
    }

    static int firstUniqueString(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0, tmp = 0; i < chars.length; i++) {
            tmp = tmp ^ chars[i];
            System.out.println((char) tmp);
        }
        return 0;
    }

    /**
     * 经理，我想这个星期办理离职手续可以吗？
     * 移动端的文档大致整理出来了，已经发给莫哥看，如果有需要补充的，这俩天会补充完整的。
     * @param args
     */
    public static void main(String[] args) {
        System.out.print("[String] ");

//        char[] str = "hello".toCharArray();
//        reverse(str);
//        System.out.println("reverse: " + Arrays.toString(str));

//        System.out.println("reverse: " + reverse(-2147483648));

        String s = "leetcode";
//        firstUniqueString(s);

    }

}
