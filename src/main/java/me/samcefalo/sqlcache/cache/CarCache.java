package me.samcefalo.sqlcache.cache;

import me.samcefalo.sqlcache.dao.DAO;
import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.redis.RedissonProvider;

import java.util.UUID;

public class CarCache extends RedissonCache<Car, UUID> {

    public CarCache(RedissonProvider redissonProvider, DAO<Car, UUID> dao) {
        super(redissonProvider, dao, "carMap");
    }

}
