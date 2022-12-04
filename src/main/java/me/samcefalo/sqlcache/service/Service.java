package me.samcefalo.sqlcache.service;

import me.samcefalo.sqlcache.entities.Car;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface Service<T, I> {

    Future<Car> getById(I id) throws ExecutionException, InterruptedException;

    void insert(T object);

    void update(T object);

    void delete(I id);
}
