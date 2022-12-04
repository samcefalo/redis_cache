package me.samcefalo.sqlcache.service;

public interface Service<T, I> {

    T getById(I id);

    void insert(T object);

    void update(T object);

    void delete(I id);
}
