package org.iushu.programatic.service;

import org.iushu.programatic.bean.Actor;

/**
 * @author iuShu
 * @since 3/25/21
 */
public interface ActorService {

    Actor getActor(short actor_id);

    boolean updateActor(Actor actor);

    short insertActor(Actor actor);

}
