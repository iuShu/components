package org.iushu.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.iushu.web.bean.Actor;

/**
 * @author iuShu
 * @since 4/15/21
 */
@Mapper
public interface ActorMapper {

    @Select("select actor_id, first_name, last_name, last_update from actor where actor_id = #{actor_id}")
    Actor getActor(int actor_id);

}
