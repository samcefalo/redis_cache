package me.samcefalo.sqlcache.cache;

import lombok.Getter;
import me.samcefalo.sqlcache.cache.options.MapLoader;
import me.samcefalo.sqlcache.cache.options.MapWriter;
import me.samcefalo.sqlcache.dao.DAO;
import me.samcefalo.sqlcache.redis.JedisProvider;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class RedissonCache<T, I> implements Cache<T, I> {

    @Getter
    private RMapCache<I, T> map;
    @Getter
    private final String name;
    private final RedissonClient redisson;
    private final DAO dao;

    public RedissonCache(JedisProvider jedisProvider, DAO dao, String name) {
        this.name = name;
        this.redisson = jedisProvider.getRedisson();
        this.dao = dao;
        this.configureMap(new WriterOptions());
    }

    public void configureMap(WriterOptions writerOptions) {
        this.map = redisson.getMapCache(this.name, MapOptions.<I, T>defaults()
                .loader(new MapLoader(dao))
                .writer(new MapWriter(dao))
                .writeMode(writerOptions.getWriteMode())
                .writeBehindDelay(writerOptions.getWriteBehindDelay())
                .writeBehindBatchSize(writerOptions.getWriteBehindBatchSize()));
    }

    @Override
    public void set(I key, T value) {
        map.put(key, value, 600, TimeUnit.SECONDS);
    }

    @Override
    public Future<T> get(I key) {
        return map.getAsync(key);
    }

    @Override
    public void delete(I key) {
        map.removeAsync(key);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
