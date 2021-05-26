package org.iushu.redis.consistency.controller;

import org.iushu.redis.consistency.service.ConsistencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author iuShu
 * @since 5/26/21
 */
@RestController
@RequestMapping("/consistency")
public class ConsistencyController {

    @Autowired
    private ConsistencyService consistencyService;

    @RequestMapping("/get")
    public int get() {
        return consistencyService.get();
    }

    @RequestMapping("/decrement")
    public boolean decrement() {
        return consistencyService.decrement();
    }

    @RequestMapping("/strategy/{key}")
    public boolean strategy(@PathVariable String key) {
        return consistencyService.strategy(key);
    }

}
