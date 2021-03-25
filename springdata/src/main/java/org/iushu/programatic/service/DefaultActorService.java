package org.iushu.programatic.service;

import org.iushu.programatic.bean.Actor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author iuShu
 * @since 3/25/21
 */
public class DefaultActorService implements ActorService {

    private JdbcTemplate jdbcTemplate;

    public DefaultActorService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Actor getActor(short actor_id) {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor WHERE actor_id = ?";
        return jdbcTemplate.queryForObject(sql, Actor.rowMapper(), actor_id);
    }

    @Override
    public boolean updateActor(Actor actor) {
        String sql = "UPDATE actor SET first_name=?, last_name=?, last_update=CURRENT_TIME WHERE actor_id = ?";
        return jdbcTemplate.update(sql, actor.getFirst_name(), actor.getLast_name(), actor.getActor_id()) > 0;
    }

    @Override
    public boolean insertActor(Actor actor) {
        String sql = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?, ?, CURRENT_TIME)";
        return jdbcTemplate.update(sql, actor.getFirst_name(), actor.getLast_name()) > 0;
    }

}
