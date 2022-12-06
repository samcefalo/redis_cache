package me.samcefalo.sqlcache.dao;

import me.samcefalo.sqlcache.dao.handlers.CarResultSetHandler;
import me.samcefalo.sqlcache.entities.Car;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

//Using DBUtils
/**
 * Implementation example of {@link DAO}
 * using {@link org.apache.commons.dbutils.DbUtils} to handle JDBC
 */
public class CarDAO implements DAO<Car, UUID> {

    private final QueryRunner runner;

    public CarDAO(DataSource dataSource) {
        this.runner = new QueryRunner(dataSource);
    }

    public Car getById(UUID id) {

        try {
            return runner.query(
                    "SELECT * FROM car WHERE id=?", new CarResultSetHandler(), id.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(Car car) {
        if (Objects.isNull(car)) {
            throw new NullPointerException("Invalid car object.");
        }

        try {
            runner.update("INSERT INTO car (id" +
                            ",name" +
                            ",age" +
                            ",machine_name" +
                            ",machine_type) VALUES (?,?,?,?,?)"
                    , car.getId().toString()
                    , car.getName()
                    , car.getAge()
                    , car.getMachine().getMachineName()
                    , car.getMachine().getMachineType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertOrUpdate(Car car) {
        if (Objects.isNull(car)) {
            throw new NullPointerException("Invalid car object.");
        }

        try {
            runner.update("INSERT INTO car (id" +
                            ",name" +
                            ",age" +
                            ",machine_name" +
                            ",machine_type) VALUES (?,?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "name= ?, age=? , machine_name = ?, machine_type = ?"
                    , car.getId().toString()
                    , car.getName()
                    , car.getAge()
                    , car.getMachine().getMachineName()
                    , car.getMachine().getMachineType()
                    //UPDATE
                    , car.getName()
                    , car.getAge()
                    , car.getMachine().getMachineName()
                    , car.getMachine().getMachineType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Car car) {
        if (Objects.isNull(car)) {
            throw new NullPointerException("Invalid car object.");
        }

        try {
            runner.update("UPDATE car SET name = ?" +
                            ",age = ?" +
                            ",machine_name = ?" +
                            ",machine_type = ? WHEN id = ?"
                    , car.getName()
                    , car.getAge()
                    , car.getMachine().getMachineName()
                    , car.getMachine().getMachineType()
                    , car.getId().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(UUID id) {
        try {
            runner.update("DELETE FROM car WHERE id=?", id.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
