package com.iushu.cambridge.dictionary.service;

import org.springframework.stereotype.Service;

import static com.iushu.cambridge.dictionary.utils.Tokenizer.filter;

/**
 * Created by iuShu on 19-3-29
 */
@Service
public class SearchService {

    public void search(String input) {
        input = filter(input);
    }

    public void word(String word) {

    }

}
