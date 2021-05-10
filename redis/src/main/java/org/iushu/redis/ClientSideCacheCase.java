package org.iushu.redis;

/**
 * Client Side Cache
 *  Tracking (supports by Redis 6 or higher)
 *
 *  Invalidation Table
 *      the server maintains a invalidation table. it contain a maximum number of entries
 *      to avoid storing too much data, an old key may be evicted as a new key inserted.
 *      in this case, the server will sending an invalidation message to the clients.
 *
 *  Avoiding race conditions
 *      conn A: get foo
 *      conn B: invalidate foo
 *      conn A: bar (received a stale version)
 *      Solution
 *      conn A: get foo
 *      conn B: invalidate foo
 *      conn B: delete foo from the local cache
 *      conn A: bar (received a stale version)
 *      conn A: check from local cache if key 'foo' is existed, discard if not existed.
 *
 * @author iuShu
 * @since 5/7/21
 */
public class ClientSideCacheCase {

    /**
     * Tracing (default)
     *  the server remembers what keys a client accessed and send invalidation messages
     *  when the same keys are modified. this cost memory in the server side.
     */
    static void tracingMode() {

    }

    /**
     * Broadcasting
     *  the server does not remembers what keys the client accessed, so it does not cost any memory at all
     *  at the server side. clients subscribe to key prefixes and will receive a notification message every time
     *  a key matching such prefix is touched.
     */
    static void broadcastingMode() {

    }

    public static void main(String[] args) {

    }

}
