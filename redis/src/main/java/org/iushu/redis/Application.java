package org.iushu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {

    static Jedis jedis() {
        return new Jedis("localhost", 6379);
    }

    static void finish(Jedis jedis) {
        jedis.flushAll();
        jedis.close();
    }

    static void getAndSetRangeCase(Jedis jedis) {
        jedis.set("ver", "82322");
        String ret = jedis.getrange("ver", 1, 3);
        System.out.println(ret);
    }

    /**
     * atomic set multiple key values in one command
     * msetnx: one set failed, all failed.
     */
    static void multipleGetSetCase(Jedis jedis) {
        List<String> multiples = new ArrayList<>();

        multiples.add("ver");
        multiples.add("beta-6.1.2319");

        multiples.add("platform");
        multiples.add("beta-6.1.2319");

        multiples.add("sessionId");
        multiples.add("ise@8jsj287wW21lkl23aMd1s8896Ax");

        String[] keyValues = multiples.toArray(new String[0]);
        String result = jedis.mset(keyValues);
        System.out.println(">> " + result);
    }

    /**
     * list can be used as stack, queue and blocking queue
     * list allows duplicate elements and ordered by insert time
     * NOTE: redis deletes empty list
     *
     * @see Jedis#type(String) return list
     * @see Jedis#llen(String) list.size()
     * @see Jedis#lindex(String, long) get element with specified index (not dequeue)
     * @see Jedis#lrange(String, long, long) get elements with specified ranged indices (not dequeue)
     * @see Jedis#lrem(String, long, String) remove elements
     * @see Jedis#ltrim(String, long, long)
     * @see Jedis#rpoplpush(String, String) pop from source and push in target (ATOMIC)
     * @see Jedis#lset(String, long, String) modify value at specified index (error if key not found)
     * @see Jedis#linsert(String, ListPosition, String, String) insert at specified index
     *
     * Stack (FILO)
     * @see Jedis#lpush(String, String...)
     * @see Jedis#lpop(String)
     *
     * @see Jedis#rpush(String, String...)
     * @see Jedis#rpop(String)
     *
     * Queue (FIFO)
     * @see Jedis#lpush(String, String...)
     * @see Jedis#rpop(String)
     *
     * @see Jedis#rpush(String, String...)
     * @see Jedis#lpop(String)
     *
     * Blocking Queue
     * @see Jedis#brpop(String...) pop firsst or blocking util timeout
     * @see Jedis#blpop(String...) pop last or blocking utils timeout
     *
     */
    static void listCase(Jedis jedis) {

    }

    /**
     * set does not allows duplicate elements and unordered
     * NOTE: redis deletes empty set
     *
     * @see Jedis#type(String) return set
     * @see Jedis#sadd(String, String...)
     * @see Jedis#smembers(String) all elements
     * @see Jedis#sismember(String, String) set.contains()
     * @see Jedis#srem(String, String...) remove elements
     * @see Jedis#scard(String) set.size()
     * @see Jedis#srandmember(String, int) random get elements
     * @see Jedis#spop(String) random pop a element
     * @see Jedis#smove(String, String, String) move specified element from source to target (ATOMIC)
     *
     * UNION INTERSECTION SUBTRACTION
     * @see Jedis#sdiff(String...) find different elements at multiple sets
     * @see Jedis#sunion(String...) union elements in multiple sets
     * @see Jedis#sinter(String...) find same elements at multiple sets
     */
    static void setCase(Jedis jedis) {

    }

    /**
     * zset is a sorted set (default order is ASCent)
     *
     * @see Jedis#zadd(String, double, String) add element with score
     * @see Jedis#zscore(String, String) get score of element
     * @see Jedis#zmscore(String, String...) get scores of multiple elements
     * @see Jedis#zrange(String, long, long) get elements with specified ranged indices
     * @see Jedis#zrangeByScore(String, double, double) get elements with specified ranged score
     * @see Jedis#zrangeByScoreWithScores(String, double, double) return elements with their scores
     * @see Jedis#zrem(String, String...) remove members
     * @see Jedis#zcard(String) zset.size()
     * @see Jedis#zrevrange(String, long, long) sort the set in DESCent mode
     * @see Jedis#zrank(String, String) the rank of given element
     * @see Jedis#zcount(String, double, double) count elements that the score between the range
     *
     * @see Jedis#zrangeByScore(String, double, double) -inf +inf (see more functional usage)
     */
    static void zsetCase(Jedis jedis) {

    }

    /**
     * map in redis
     * NOTE: redis deletes empty map
     *
     * @see Jedis#hset(String, String, String) map.put()
     * @see Jedis#hset(String, Map) map.putAll()
     * @see Jedis#hsetnx(String, String, String) set entry if no exist
     * @see Jedis#hget(String, String) map.get()
     * @see Jedis#hmget(String, String...) get multiple values by keys
     * @see Jedis#hmset(String, Map) set multiple keys and values (one set failed, all failed)
     * @see Jedis#hgetAll(String) get all the keys and values
     * @see Jedis#hdel(String, String...) delete entries in map
     * @see Jedis#hlen(String) map.size()
     * @see Jedis#hexists(String, String) map.contains()
     * @see Jedis#hkeys(String) map.keySet()
     * @see Jedis#hvals(String) map.values()
     * @see Jedis#hincrBy(String, String, long) increase the value with specified num
     */
    static void mapCase(Jedis jedis) {

    }

    static void geospatial(Jedis jedis) {

    }

    public static void main(String[] args) {
        Jedis jedis = jedis();
        try {

//            getAndSetRangeCase(jedis);
            multipleGetSetCase(jedis);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish(jedis);
//            jedis.close();
        }
    }

}
