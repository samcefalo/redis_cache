package me.samcefalo.sqlcache.redis;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Provider of {@link RedissonClient}
 */
public class RedissonProvider implements RedisProvider {

    @Getter
    private final RedissonClient redisson;

    public RedissonProvider(String url, int port) {
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://" + url + ":" + port);

        this.redisson = Redisson.create(config);
    }

    public RedissonProvider(Config config) {
        this.redisson = Redisson.create(config);
    }

    @Override
    public void close() {

    }

}
