package org.eureka.stockAnalytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {

    @Autowired
    DataSource dataSource;

    @Bean(value = "jdbcTemplate")
    public JdbcTemplate getJDBCTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
