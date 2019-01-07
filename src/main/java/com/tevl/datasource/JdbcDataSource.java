package com.tevl.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.logging.Logger;

//TODO if you do not intend to provide a solution, why even have this class
//TODO and if you provide a solution then take javax.sql.DataSource as an input, do not build a datasource of your own.
public abstract class JdbcDataSource implements DataSource {

    private final javax.sql.DataSource dataSource;

    private static final Logger LOGGER = Logger.getLogger(JdbcDataSource.class.getName());

    protected JdbcDataSource(String username, char[] password, String jdbcUrl)
    {
        LOGGER.info("Initializing hikari datasource with jdbc url "+jdbcUrl);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(String.valueOf(password));

        this.dataSource = new HikariDataSource(config);
    }

    public final javax.sql.DataSource getDataSource() {
        return dataSource;
    }

}
