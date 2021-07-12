package org.iushu.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redisson Demo
 *
 * @author iuShu
 * @since 7/12/21
 */
public class Application {

    static void gettingStart() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        RedissonClient redisson = Redisson.create(config);
        RMap redissonMap = redisson.getMap("usr:8812");

//        redissonMap.put("name", "Michael");
//        redissonMap.put("gender", 1);
//        redissonMap.put("age", 8);

        System.out.println(redissonMap.get("name"));
        System.out.println(redissonMap.get("gender"));
        System.out.println(redissonMap.get("age"));

        redisson.shutdown();
    }

    static void lock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        RedissonClient redisson = Redisson.create(config);
        RLock rlock = redisson.getLock("lock:usr");
        rlock.lock();
        try {
            System.out.println("locked: " + rlock.isLocked());
            System.out.println("held: " + rlock.isHeldByCurrentThread());
            System.out.println("hold: " + rlock.getHoldCount());
            System.out.println("ttl: " + rlock.remainTimeToLive());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rlock.unlock();
        }
        redisson.shutdown();
    }

    public static void main(String[] args) {
//        gettingStart();
        lock();
    }

}
