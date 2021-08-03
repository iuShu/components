package org.iushu.tutorial.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author iuShu
 * @since 8/2/21
 */
@RestController
@Profile("provider")
public class ProviderController {

    @RequestMapping("/provider/stock")
    public String stock() {
        return "stock: " + new Random().nextInt(10000);
    }

}
