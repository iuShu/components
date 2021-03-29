package org.iushu.project.service;

import org.iushu.project.bean.Actor;
import org.iushu.project.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.iushu.project.utils.PageUtils.limitBegin;

/**
 * @author iuShu
 * @since 3/29/21
 */
@Service
public class DefaultActorService implements ActorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Actor> ROWMAPPER_ACTOR = (rs, rowNum) -> {
        Actor actor = new Actor();
        actor.setActor_id(rs.getShort(1));
        actor.setFirst_name(rs.getString(2));
        actor.setLast_name(rs.getString(3));
        actor.setLast_update(rs.getTimestamp(4));
        return actor;
    };

    @Override
    public List<Actor> getActors(int pageNo, int pageSize) {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor LIMIT ?, ?";
        return jdbcTemplate.query(sql, ROWMAPPER_ACTOR, limitBegin(pageNo, pageSize), pageSize);
    }

    @Override
    public Actor getActor(short actor_id) {
        String sql = "SELECT * FROM actor WHERE actor_id = ?";
        return jdbcTemplate.queryForObject(sql, ROWMAPPER_ACTOR, actor_id);
    }

}
