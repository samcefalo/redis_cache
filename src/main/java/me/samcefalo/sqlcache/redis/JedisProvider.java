package me.samcefalo.sqlcache.redis;

import lombok.Getter;
import redis.clients.jedis.JedisPooled;

public class JedisProvider {

    @Getter
    private JedisPooled jedisPooled;

    public JedisProvider(String url, int port) {
        this.jedisPooled = new JedisPooled(url, port);
    }

}
