package me.samcefalo.sqlcache.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Getter;

import javax.sql.DataSource;

public class MySqlProvider implements DataSourceProvider {

    @Getter
    private String host, database, user, password, url;

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
