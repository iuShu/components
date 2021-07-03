package org.iushu.weboot.mapper;

import org.apache.ibatis.annotations.*;
import org.iushu.weboot.bean.Actor;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Mapper
public interface ActorMapper {

    @Select("select actor_id, first_name, last_name, last_update from actor where actor_id = #{actor_id}")
    Actor getActor(short actor_id);

    @Update("update actor set first_name = #{first_name}, last_name = #{last_name}, last_update=current_timestamp where actor_id = #{actor_id}")
    boolean updateActor(Actor actor);

    @Insert("insert into actor(first_name, last_name, last_update) values(#{first_name}, #{last_name}, current_timestamp)")
    boolean insertActor(Actor actor);

    @Delete("delete from actor where actor_id = #{actor_id}")
    boolean deleteActor(short actor_id);

}
