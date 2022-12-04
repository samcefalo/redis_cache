package me.samcefalo.sqlcache;

import me.samcefalo.sqlcache.redis.JedisProvider;

public class JedisFactory {

    private static JedisProvider provider = new JedisProvider("localhost", 6379);

    public static JedisProvider JedisProviderInstance() {
        return provider;
    }
}
