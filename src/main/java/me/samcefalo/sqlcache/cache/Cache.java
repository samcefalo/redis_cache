package me.samcefalo.sqlcache.cache;

import org.redisson.api.RMap;

import java.util.concurrent.Future;

public interface Cache<T, I> {

    void set(I key, T value);

    Future<T> get(I key);

    void delete(I key);

    void clear();

    RMap getMap();

}
