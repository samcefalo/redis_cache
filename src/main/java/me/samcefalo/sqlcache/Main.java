package me.samcefalo.sqlcache;

import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.entities.Machine;
import me.samcefalo.sqlcache.database.DataSourceProvider;
import me.samcefalo.sqlcache.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        DataSourceProvider provider = DataSourceFactory.HikariProviderInstance();
        //DataSourceProvider provider = DataSourceFactory.MySqlProviderInstance();
        //DataSourceProvider provider = DataSourceFactory.SqliteProviderInstance();

        Machine machine = new Machine("V8 Spider", "Combustion");
        Car car = Car.builder().id(UUID.randomUUID()).name("Ferrari").age("2000").machine(machine).build();

        CarService carService = new CarService(provider.getDataSource());

        carService.insert(car);

        System.out.println(carService.getById(car.getId()));

    }

}
