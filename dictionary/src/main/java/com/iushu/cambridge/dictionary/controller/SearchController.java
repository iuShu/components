package com.iushu.cambridge.dictionary.controller;

import com.iushu.cambridge.dictionary.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.iushu.cambridge.dictionary.utils.Tokenizer.filter;

/**
 * Created by iuShu on 19-3-29
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public void search(String input) {
        searchService.search(input);
    }

}
