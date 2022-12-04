package me.samcefalo.sqlcache.database;

import lombok.Getter;

import javax.sql.DataSource;

public class HikariProvider implements DataSourceProvider{

    @Getter
    private String host, database, user, password, url;

    public HikariProvider(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.url = "jdbc:mysql://" + host + "/" + database;
    }

    public DataSource getDataSource() {
        com.zaxxer.hikari.HikariDataSource hikari = new com.zaxxer.hikari.HikariDataSource();
        hikari.setJdbcUrl(this.url);
        hikari.addDataSourceProperty("user", this.user);
        hikari.addDataSourceProperty("password", this.password);
        //hikari.addDataSourceProperty("useSSL", false);
        hikari.addDataSourceProperty("cachePrepStmts", "true");
        hikari.addDataSourceProperty("prepStmtCacheSize", "250");
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return hikari;
    }

}
