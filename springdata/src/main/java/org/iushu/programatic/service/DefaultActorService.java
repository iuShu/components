package org.iushu.programatic.service;

import org.iushu.programatic.bean.Actor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

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

    // return generated key in JdbcTemplate after insert
    @Override
    public short insertActor(Actor actor) {
        String sql = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?, ?, CURRENT_TIME)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"actor_id"});
            statement.setString(1, actor.getFirst_name());
            statement.setString(2, actor.getLast_name());
            return statement;
        }, keyHolder);
        return affectedRow > 0 ? (short) keyHolder.getKey() : 0;
    }

}
