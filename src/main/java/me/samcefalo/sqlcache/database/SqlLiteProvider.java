package me.samcefalo.sqlcache.database;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * Provider of {@link SqlLiteProvider}
 */
public class SqlLiteProvider implements DataSourceProvider {

    private final String path;
    private final String url;

    public SqlLiteProvider(String path) {
        this.path = path;
        this.url = "jdbc:sqlite:" + path;
    }

    public String getDriverClass() {
        return "org.sqlite.JDBC";
    }

    @Override
    public DataSource getDataSource() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl(url);
        return sqLiteDataSource;
    }
}
