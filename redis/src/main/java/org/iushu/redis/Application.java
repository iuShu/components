package org.iushu.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {

    static Jedis jedis() {
        return new Jedis("localhost", 6379);
    }

    /**
     * @see Jedis#flushDB() flush all the keys under the current selected db
     * @see Jedis#flushAll() flush all the keys throughout all db in Redis
     */
    static void finish(Jedis jedis) {
        jedis.flushAll();
        jedis.close();
    }

    /**
     * String binary-safe key
     *  very short/long keys are not a good idea.
     *  the maximum allowed key size is 512MB.
     */
    static void getAndSetRangeCase(Jedis jedis) {
        jedis.set("ver", "82322");
        String ret = jedis.getrange("ver", 1, 3);
        System.out.println(ret);
    }

    /**
     * Multiple keys and values command (ATOMIC)
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
     * List can be used as stack, queue and blocking queue
     * List allows duplicate elements and ordered by insert time
     * NOTE: Redis deletes empty list
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
     * Set does not allows duplicate elements and unordered
     * NOTE: Redis deletes empty set
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
     * ZSet is a sorted set (default order is ASCent)
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
     * Map in Redis
     * NOTE: Redis deletes empty map
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
     * @see Jedis#hincrByFloat(String, String, double) increase the value by float
     * @see Jedis#hrandfield(String, long) return random keys
     * @see Jedis#hrandfieldWithValues(String, long) return a random entry
     * @see Jedis#hstrlen(String, String) return value's length
     */
    static void mapCase(Jedis jedis) {

    }

    /**
     * Geospatial is actually a zset and can be operated by zset's commands
     * Geospatial zset using a geohash-encoded 52 bits unsigned integer as score
     *
     * @see Jedis#type(String) return zset
     * @see Jedis#geoadd(String, double, double, String) add a geospatial
     * @see Jedis#geodist(String, String, String, GeoUnit) return the distance of two members
     * @see Jedis#geohash(String, String...) convert the coordinate to the hash string
     * @see Jedis#geopos(String, String...) return longitude and latitude of the member
     * @see Jedis#georadius(String, double, double, double, GeoUnit) radius search by longitude and latitude
     * @see Jedis#georadiusByMember(String, String, double, GeoUnit) radius search by members' name
     */
    static void geospatial(Jedis jedis) {

    }

    /**
     * HyperLogLog for statistical counting with non-repeating members
     *
     * @see Jedis#type(String) return string
     * @see Jedis#pfadd(String, String...)
     * @see Jedis#pfcount(String...)
     * @see Jedis#pfmerge(String, String...)
     */
    static void hyperLogCase(Jedis jedis) {

    }

    /**
     * BitMap for recording and counting in each bits (like punched-in/out)
     *
     * @see Jedis#setbit(String, long, boolean)
     * @see Jedis#getbit(String, long)
     * @see Jedis#bitcount(String, long, long)
     * @see Jedis#bitpos(String, boolean)
     */
    static void bitmapCase(Jedis jedis) {

    }

    /**
     * Transaction in Redis (more like batch process)
     *   one command enqueued failed, all enqueued commands failed.
     *   partial command success is allowed in execution.
     *
     * @see redis.clients.jedis.Transaction
     * @see Jedis#multi() open a transaction then enqueue commands
     * @see Transaction#exec() execute enqueued commands
     * @see Transaction#discard() discard enqueued commands
     */
    static void transactionCase() {
        Jedis jedis = jedis();
        Transaction tx = jedis.multi();     // open a transaction
        try {
            tx.set("cmd1", "aa");   // enqueue tx command
            tx.set("cmd2", "bb");   // enqueue tx command
            tx.set("cmd3", "cc");   // enqueue tx command
            tx.exec();              // execute tx commands
        } catch (Exception e) {
            tx.discard();           // discard all command if exception occurred
            e.printStackTrace();
        } finally {
            jedis.close();          // close the connection eventually
        }
    }

    /**
     * Optimistic locking using compare-and-set (CAS)
     *   compare the version and exec commands
     *
     * when EXEC is called, all keys are unwatched, regardless of whether the transaction was aborted or not.
     * when a client connection is closed, everything gets unwatched.
     *
     * unwatch command would flush all the watched keys.
     *
     * @see Jedis#watch(String...)
     * @see Jedis#unwatch()
     *
     * @see #lockForAtomicZPOP(Jedis) implement a atomic case by using watch mechanism
     */
    static void lockCase(Jedis jedis) {
        jedis.watch("balance");  // watch the version of the key
        Transaction tx = jedis.multi();
        tx.decrBy("balance", 80);
        tx.exec();  // compare the version and commit the operation or discard it if version changed

        // if current values does not reach the condition and we don't want to proceed,
        // unwatch the key for preparing the next transaction
        jedis.unwatch();
    }

    static void lockForAtomicZPOP(Jedis jedis) {
        if (!"OK".equals(jedis.watch("zset")))
            return;

        Set<String> zset = jedis.zrange("zset", 0 , -1);
        Transaction tx = jedis.multi();
        tx.zrem("zset", zset.toArray(new String[0]));
        tx.exec();
    }

    /**
     * Using pipelining to speedup Redis queries
     *
     * Redis is a TCP server using the client-server model.
     * RTT(Round Trip Time) can affect performance when a client requests to Redis.
     *
     * Pipeline is not just about the RTT, it actually greatly improves the number of
     * operations you can perform per second in a given Redis server.
     */
    static void pipelineCase(Jedis jedis) {
        Pipeline pipeline = jedis.pipelined();

        pipeline.set("pkey", "aa");
        pipeline.incr("sum");

        pipeline.exec();

        pipeline.discard();

        pipeline.close();
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
