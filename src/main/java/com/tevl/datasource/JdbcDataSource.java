package com.tevl.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.logging.Logger;

public abstract class JdbcDataSource implements DataSource {

    private javax.sql.DataSource dataSource;

    private static Logger LOGGER = Logger.getLogger(JdbcDataSource.class.getName());

    public JdbcDataSource(String username,char[] password,String jdbcUrl)
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
