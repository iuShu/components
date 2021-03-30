package org.iushu.project.controller;

import org.iushu.project.bean.Actor;
import org.iushu.project.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author iuShu
 * @since 3/29/21
 */
@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @RequestMapping("/list/{pageNo}")
    public List<Actor> list(@PathVariable int pageNo) {
        return actorService.getActors(pageNo, DEFAULT_PAGE_SIZE);
    }

    @RequestMapping("/filter/{actor_id}")
    public Actor get(@PathVariable short actor_id) {
        return actorService.getActor(actor_id);
    }

}
