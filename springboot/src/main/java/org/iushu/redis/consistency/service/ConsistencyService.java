package org.iushu.redis.consistency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

/**
 * @author iuShu
 * @since 5/26/21
 */
@Service
public class ConsistencyService {

    private static final String REDIS_KEY = "consistency";
    private static final String SQL_SELECT = "select quantity from seckilling.consistency where id = 1";
    private static final String SQL_UPDATE = "update seckilling.consistency set quantity = ? where id = 1";
    private final Object monitor = new Object();
    private ConsistencyStrategy consistencyStrategy = ConsistencyStrategy.UDUC;
    private static ReentrantLock databaseLock = new ReentrantLock();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int get() {
        Object quantity = redisTemplate.opsForValue().get(REDIS_KEY);
        if (quantity == null) {    // load from database
            synchronized (monitor) {
                if (quantity == null) {
                    quantity = jdbcTemplate.queryForObject(SQL_SELECT, Integer.class);
                    redisTemplate.opsForValue().set(REDIS_KEY, quantity);
                }
            }
        }
        return Integer.valueOf(quantity.toString());
    }

    public boolean decrement() {
        return consistencyStrategy.execute(jdbcTemplate, redisTemplate);
    }

    public boolean strategy(String key) {
        ConsistencyStrategy consistencyStrategy = ConsistencyStrategy.valueOf(key);
        if (consistencyStrategy == null)
            return false;
        this.consistencyStrategy = consistencyStrategy;
        return true;
    }

    enum ConsistencyStrategy {

        /**
         * update database update cache
         *
         * A: ... UD-90 ..................... UC-90 ...  [dirty cache]
         * B: .......... UD-89 .... UC-89 .............
         */
        UDUC((jdbc, redis) -> {
            Integer quantity, before;
            databaseLock.lock();    // mutual exclusive
            try {
                quantity = jdbc.queryForObject(SQL_SELECT, Integer.class);
                before = quantity;
                if (jdbc.update(SQL_UPDATE, --quantity) < 1)
                    return false;
            } finally {
                databaseLock.unlock();
            }

            redis.opsForValue().set(REDIS_KEY, quantity);
            System.out.println(Thread.currentThread().getName() + " get " + before + " and set " + quantity);
            return true;
        }),

        /**
         * delete cache update database
         *
         * A: ... DC-90 ............................. UD-89 ...
         * B: ........... GET .. LOAD-90 .. CACHE-90 ..........  [dirty cache]
         */
        DCUD((jdbc, redis) -> {
            redis.delete(REDIS_KEY);

            databaseLock.lock();    // mutual exclusive
            try {
                Integer quantity = jdbc.queryForObject(SQL_SELECT, Integer.class);
                return jdbc.update(SQL_UPDATE, quantity - 1) > 0;
            } finally {
                databaseLock.unlock();
            }
        }),

        /**
         * update database delete cache
         *
         * A: ..................... UD-89 .. DC ...............
         * B: ... GET .. LOAD-90 ................. CACHE-90 ...  [dirty cache]
         */
        UDDC((jdbc, redis) -> {
            databaseLock.lock();    // mutual exclusive
            try {
                Integer quantity = jdbc.queryForObject(SQL_SELECT, Integer.class);
                if (jdbc.update(SQL_UPDATE, quantity - 1) < 1)
                    return false;
            } finally {
                databaseLock.unlock();
            }

            redis.delete(REDIS_KEY);
            return true;
        }),

        ;

        private BiFunction<JdbcTemplate, RedisTemplate, Boolean> biFunction;

        ConsistencyStrategy(BiFunction<JdbcTemplate, RedisTemplate, Boolean> biFunction) {
            this.biFunction = biFunction;
        }

        public boolean execute(JdbcTemplate jdbcTemplate, RedisTemplate redisTemplate) {
            return biFunction.apply(jdbcTemplate, redisTemplate);
        }

    }

}
