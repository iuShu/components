package org.iushu.project.service;

import org.iushu.project.bean.Actor;

import java.util.List;

/**
 * @author iuShu
 * @since 3/29/21
 */
public interface ActorService {

    List<Actor> getActors(int pageNo, int pageSize);

    Actor getActor(short actor_id);

}
