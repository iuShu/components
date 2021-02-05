package com.iushu.cambridge.dictionary.utils;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by iuShu on 19-3-29
 */
public class Tokenizer {

    public static String filter(String input) {
        if (isBlank(input))

    }

    public static boolean isWord(String input) {
        if (input.contains(" "))
            return false;
        return true;
    }

}
