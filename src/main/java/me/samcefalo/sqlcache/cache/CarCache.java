package me.samcefalo.sqlcache.cache;

import me.samcefalo.sqlcache.dao.DAO;
import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.redis.JedisProvider;

import java.util.UUID;

public class CarCache extends RedissonCache<Car, UUID> {

    public CarCache(JedisProvider jedisProvider, DAO<Car, UUID> dao) {
        super(jedisProvider, dao, "carMap");
    }

}
