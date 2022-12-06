package me.samcefalo.sqlcache.dao.handlers;

import me.samcefalo.sqlcache.entities.Car;
import me.samcefalo.sqlcache.entities.Machine;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Implementation example of {@link ResultSetHandler}
 */
public class CarResultSetHandler implements ResultSetHandler<Car> {

    @Override
    public Car handle(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Car.builder()
                    .id(UUID.fromString(resultSet.getString("id")))
                    .name(resultSet.getString("name"))
                    .age(resultSet.getString("age"))
                    .machine(new Machine(resultSet.getString("machine_name")
                            , resultSet.getString("machine_type")))
                    .build();
        }
        return null;
    }

}
