package me.samcefalo.sqlcache.redis;

import lombok.Getter;
import redis.clients.jedis.JedisPooled;

public class JedisProvider {

    @Getter
    private final JedisPooled jedisPooled;

    public JedisProvider(String url, int port) {
        this.jedisPooled = new JedisPooled(url, port);
    }


    public void close() {
        this.jedisPooled.close();
    }

}
