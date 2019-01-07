package com.tsexpval.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class JdbcDataSource implements DataSource {

    private javax.sql.DataSource dataSource;

    public JdbcDataSource(String username,char[] password,String jdbcUrl)
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(new String(password));

        this.dataSource = new HikariDataSource(config);
    }

    public final javax.sql.DataSource getDataSource() {
        return dataSource;
    }

}
