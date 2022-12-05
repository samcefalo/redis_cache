package me.samcefalo.sqlcache.cache;

import com.google.gson.Gson;
import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.redis.JedisProvider;
import redis.clients.jedis.JedisPooled;

public class CarCache implements Cache<Car> {

    private final int cacheTimeSeconds = 600;
    private final JedisPooled jedisPooled;

    public CarCache(JedisProvider jedisProvider) {
        this.jedisPooled = jedisProvider.getJedisPooled();
    }

    @Override
    public void set(String key, Car value) {
        Gson gson = new Gson();
        this.jedisPooled.setex(key, cacheTimeSeconds, gson.toJson(value));
    }

    @Override
    public Car get(String key) {
        Gson gson = new Gson();
        String json = jedisPooled.get(key);
        return gson.fromJson(json, Car.class);
    }

    @Override
    public void delete(String key) {
        this.jedisPooled.del(key);
    }

}
