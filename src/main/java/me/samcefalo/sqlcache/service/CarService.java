package me.samcefalo.sqlcache.service;

import me.samcefalo.sqlcache.JedisFactory;
import me.samcefalo.sqlcache.cache.CarCache;
import me.samcefalo.sqlcache.dao.CarDAO;
import me.samcefalo.sqlcache.entities.Car;

import javax.sql.DataSource;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CarService implements Service<Car, UUID> {

    private final CarCache carCache;
    private final CarDAO carDAO;
    private final ExecutorService threadPool;
    //TODO make atomic

    public CarService(DataSource dataSource) {
        this.threadPool = Executors.newFixedThreadPool(10);
        this.carDAO = new CarDAO(dataSource);
        this.carCache = new CarCache(JedisFactory.JedisProviderInstance());
    }

    @Override
    public Future<Car> getById(UUID id) {
        return this.runAsync(() -> {
            Car car = this.carCache.get("car:" + id);
            if (car != null) {
                System.out.println("Getting by Cache");
                return car;
            } else {
                System.out.println("Getting by Database");
                car = this.carDAO.getById(id);
                this.carCache.set("car:" + id, car);
                return car;
            }
        });
    }

    @Override
    public void insert(Car car) {
        this.runAsync(() -> {
            this.carDAO.insert(car);
            this.carCache.set("car:" + car.getId(), car);
        });
    }

    @Override
    public void update(Car car) {
        this.runAsync(() -> {
            this.carDAO.update(car);
            this.carCache.set("car:" + car.getId(), car);
        });
    }

    @Override
    public void delete(UUID id) {
        this.runAsync(() -> {
            this.carCache.delete("car:" + id);
            this.carDAO.delete(id);
        });
    }

    //run in another Thread
    private Future<Car> runAsync(final Callable<Car> callBack) {
        return this.threadPool.submit(callBack);
    }

    private void runAsync(final Runnable runnable) {
        this.threadPool.submit(runnable);
    }

}
