package org.iushu.web.controller;

import org.iushu.web.bean.Actor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author iuShu
 * @since 4/6/21
 */
@RestController
@RequestMapping("/trace")
public class TraceController {

    @RequestMapping("/actor")
    public Actor getActor() {
        Actor actor = new Actor();
        actor.setActor_id((short) 34);
        actor.setFirst_name("Rod");
        actor.setLast_name("Johnson");
        actor.setLast_update(new Date());
        return actor;
    }

}
