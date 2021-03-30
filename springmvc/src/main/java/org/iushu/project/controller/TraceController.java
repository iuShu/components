package org.iushu.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iuShu
 * @since 3/30/21
 */
@RestController
@RequestMapping("/trace")
public class TraceController {

    @RequestMapping(value = "/param", params = "name=iuShu")
    public String requestParameterCondition() {
        return "Request's parameter matched";
    }

    @RequestMapping(value = "/header", headers = "Host=www.iushu.com:8080")
    public String requestHeaderCondition() {
        return "Request's header matched";
    }

}
