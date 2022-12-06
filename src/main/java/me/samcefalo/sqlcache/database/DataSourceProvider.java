package me.samcefalo.sqlcache.database;

import javax.sql.DataSource;

/**
 * Provider of {@link DataSource}
 */
public interface DataSourceProvider {

    DataSource getDataSource();

}
