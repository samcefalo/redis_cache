package me.samcefalo.sqlcache.redis;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class JedisProvider {

    /*
    @Getter
    private final JedisPooled jedisPooled;
    */
    @Getter
    private final RedissonClient redisson;

    public JedisProvider(String url, int port) {
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://" + url + ":" + port);

        redisson = Redisson.create(config);

        //this.jedisPooled = new JedisPooled(url, port);
    }


    public void close() {
        //this.jedisPooled.close();
    }

}
