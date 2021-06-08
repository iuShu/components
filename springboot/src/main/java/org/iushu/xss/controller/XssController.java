package org.iushu.xss.controller;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iuShu
 * @since 6/8/21
 */
@RestController
@RequestMapping("/xss")
public class XssController {

    @RequestMapping("/access/{key}")
    public Map<String, Object> access(@PathVariable String key) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("key", key);
        return map;
    }

}
