package me.samcefalo.sqlcache.database;

import javax.sql.DataSource;

public interface DataSourceProvider {

    DataSource getDataSource();

}
