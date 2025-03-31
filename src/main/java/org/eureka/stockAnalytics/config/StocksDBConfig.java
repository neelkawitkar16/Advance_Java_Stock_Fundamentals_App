package org.eureka.stockAnalytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

//Both JDBC Templates are injected into Spring Application Context here
@Configuration
public class StocksDBConfig {

    @Autowired
    DataSource dataSource;

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJDBCTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
