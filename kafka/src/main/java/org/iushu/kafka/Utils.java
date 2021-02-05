package org.iushu.kafka;

import java.util.List;
import java.util.Random;

/**
 * Created by iuShu on 19-2-11
 */
public class Utils {

    public static final String[] alphabet = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "A", "b", "B", "c", "C", "d", "D", "e", "E", "f", "F", "g", "G",
            "h", "H", "i", "I", "j", "J", "k", "K", "l", "L", "m", "M", "n", "N",
            "o", "O", "p", "P", "q", "Q", "r", "R", "s", "S", "t", "T",
            "u", "U", "v", "V", "w", "W", "x", "X", "y", "Y", "z", "Z"
    };

    public static Random random = new Random();

    public static String randomString(int length) {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < length; i++)
            stb.append(alphabet[random.nextInt(alphabet.length)]);
        return stb.toString();
    }

}