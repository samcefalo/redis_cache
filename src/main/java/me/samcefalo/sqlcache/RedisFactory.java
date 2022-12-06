package me.samcefalo.sqlcache;

import me.samcefalo.sqlcache.redis.RedissonProvider;

public class RedisFactory {

    private static final RedissonProvider provider = new RedissonProvider("localhost", 6379);

    public static RedissonProvider RedissonProviderInstance() {
        return provider;
    }
}
