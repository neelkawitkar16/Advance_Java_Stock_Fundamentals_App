package org.eureka.stockAnalytics.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"org.eureka.stockAnalytics.repository.crud"},
entityManagerFactoryRef = "entityManagerFactoryCrud", transactionManagerRef = "transactionManagerCrud")
@EntityScan(basePackages = {"org.eureka.stockAnalytics.entity.crud"})
public class CrudDBConfig {

    @Autowired
    DataSource crudDataSource;

    @Bean(value = "entityManagerFactoryCrud")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(crudDataSource);
        emf.setPackagesToScan("org.eureka.stockAnalytics.entity.crud");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        emf.setJpaVendorAdapter(vendorAdapter);
        return emf;
    }

    @Bean(value = "transactionManagerCrud")
    public JpaTransactionManager getTransactionManger(@Qualifier(value = "entityManagerFactoryCrud") EntityManagerFactory entityManagerFactoryBean) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean);

        return jpaTransactionManager;
    }
}
