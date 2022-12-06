package me.samcefalo.sqlcache;

import me.samcefalo.sqlcache.redis.RedissonProvider;

public class JedisFactory {

    private static final RedissonProvider provider = new RedissonProvider("localhost", 6379);

    public static RedissonProvider JedisProviderInstance() {
        return provider;
    }
}
