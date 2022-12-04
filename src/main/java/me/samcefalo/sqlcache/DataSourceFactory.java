package me.samcefalo.sqlcache;

import me.samcefalo.sqlcache.database.HikariProvider;
import me.samcefalo.sqlcache.database.MySqlProvider;
import me.samcefalo.sqlcache.database.SqlLiteProvider;

public class DataSourceFactory {

    private static HikariProvider hikariProvider = new HikariProvider("localhost:3306", "db_batch", "root", "");
    private static MySqlProvider mySqlProvider = new MySqlProvider("localhost:3306", "db_batch", "root", "");
    private static SqlLiteProvider sqliteProvider = new SqlLiteProvider("memory");

    public static HikariProvider HikariProviderInstance() {
        return hikariProvider;
    }

    public static MySqlProvider MySqlProviderInstance() {
        return mySqlProvider;
    }

    public static SqlLiteProvider SqliteProviderInstance() {
        return sqliteProvider;
    }
}
