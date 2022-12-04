package me.samcefalo.sqlcache.database;

import lombok.Getter;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

public class SqlLiteProvider implements DataSourceProvider {

    @Getter
    private String path, url;

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
