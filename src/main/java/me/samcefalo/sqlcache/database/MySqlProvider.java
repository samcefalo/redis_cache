package me.samcefalo.sqlcache.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

/**
 * Provider of {@link MysqlDataSource}
 */
public class MySqlProvider implements DataSourceProvider {

    private final String host;
    private final String database;
    private final String user;
    private final String password;
    private final String url;

    public MySqlProvider(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.url = "jdbc:mysql://" + host + "/" + database;
    }

    public String getDriverClass() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public DataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(password);
        return mysqlDataSource;
    }
}
