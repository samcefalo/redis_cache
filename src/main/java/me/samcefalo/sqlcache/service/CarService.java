package me.samcefalo.sqlcache.service;

import me.samcefalo.sqlcache.JedisFactory;
import me.samcefalo.sqlcache.cache.CarCache;
import me.samcefalo.sqlcache.dao.CarDAO;
import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.redis.JedisProvider;

import javax.sql.DataSource;
import java.util.UUID;

public class CarService implements Service<Car, UUID> {

    private CarCache carCache;
    private CarDAO carDAO;

    public CarService(DataSource dataSource) {
        this.carDAO = new CarDAO(dataSource);
        this.carCache = new CarCache(JedisFactory.JedisProviderInstance());
    }

    @Override
    public Car getById(UUID id) {
        if (this.isCached(id)) {
            System.out.println("Getting by Cache");
            return this.carCache.get("car:" + id);
        } else {
            System.out.println("Getting by Database");
            Car car = this.carDAO.getById(id);
            this.carCache.set("car:"+ id, car);
            return car;
        }
    }

    @Override
    public void insert(Car car) {
        this.carDAO.insert(car);
        this.carCache.set("car:" + car.getId(), car);
    }

    @Override
    public void update(Car car) {
        this.carDAO.update(car);
        this.carCache.set("car:" + car.getId(), car);
    }

    @Override
    public void delete(UUID id) {
        this.carCache.delete("car:" + id);
        this.carDAO.delete(id);
    }

    private boolean isCached(UUID id) {
        return this.carCache.get("car:" + id) != null;
    }

}
