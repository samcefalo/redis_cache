package me.samcefalo.sqlcache.service;

import me.samcefalo.sqlcache.RedisFactory;
import me.samcefalo.sqlcache.cache.Cache;
import me.samcefalo.sqlcache.cache.CarCache;
import me.samcefalo.sqlcache.dao.CarDAO;
import me.samcefalo.sqlcache.entities.Car;

import javax.sql.DataSource;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Implementation example of {@link Service}
 */
public class CarService implements Service<Car, UUID> {

    private final Cache<Car, UUID> cache;

    public CarService(DataSource dataSource) {
        this.cache = new CarCache(RedisFactory.RedissonProviderInstance(), new CarDAO(dataSource));
    }

    @Override
    public Future<Car> getById(UUID id) {
        return this.cache.get(id);
    }

    @Override
    public void insert(Car car) {
        this.cache.setOrUpdate(car.getId(), car);
    }

    @Override
    public void update(Car car) {
        this.cache.setOrUpdate(car.getId(), car);
    }

    @Override
    public void delete(UUID id) {
        this.cache.delete(id);
    }

}
