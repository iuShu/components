package org.iushu.web.controller;

import org.iushu.web.bean.Actor;
import org.iushu.web.mapper.ActorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private ActorMapper actorMapper;

    @RequestMapping("/actor")
    public Actor getActor() {
        Actor actor = new Actor();
        actor.setActor_id((short) 34);
        actor.setFirst_name("Rod");
        actor.setLast_name("Johnson");
        actor.setLast_update(new Date());
        return actor;
    }

    @RequestMapping("/actor/{actor_id}")
    public Actor selectActor(@PathVariable int actor_id) {
        return actorMapper.getActor(actor_id);
    }

}
