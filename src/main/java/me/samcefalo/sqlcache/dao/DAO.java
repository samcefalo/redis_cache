package me.samcefalo.sqlcache.dao;

public interface DAO<T, I> {

    T getById(I id);

    void insert(T object);

    void update(T object);

    void delete(I id);
}
