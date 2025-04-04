package org.eureka.stockAnalytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {

    /*
    Configuration properties to inject DB connection settings to Spring Application Context from
    Application properties
    Spring.datasource, all the four properties are as part of Stocks DB into the application contextdatasource
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "crudDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-crudjpa")
    public DataSource getCRUDDBDataSource() {
        return DataSourceBuilder.create().build();
    }

   /* @Autowired
    DataSource dataSource;*/

   /* @Bean(value = "jdbcTemplate")
    public JdbcTemplate getJDBCTemplate() {
        return new JdbcTemplate(dataSource);
    }*/
}
