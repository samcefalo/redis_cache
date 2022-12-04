package me.samcefalo.sqlcache.cache;

public interface Cache<T> {

    void set(String key, T value);

    T get(String key);

    void delete(String key);

}
