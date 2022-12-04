package me.samcefalo.sqlcache.cache;

import me.samcefalo.sqlcache.entities.Car;

public interface Cache<T> {

    void set(String key, T value);

    Car get(String key);

    void delete(String key);

}
