package com.wosarch.buysell.admin.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "buySellAdminEntityManagerFactory",
        transactionManagerRef = "buySellAdminTransactionManager",
        basePackages = "com.wosarch.buysell.admin.repositories")
@EnableTransactionManagement
@EnableJpaAuditing
public class BuysellAdminDatabaseConfig {

    @Autowired
    private Environment env;

    @Bean(name = "buySellAdminDataSourceProperties")
    @ConfigurationProperties("spring.buysell-admin.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "buySellAdminDataSource")
    @ConfigurationProperties("spring.buysell-admin.datasource")
    DataSource dataSource(@Qualifier("buySellAdminDataSourceProperties") DataSourceProperties properties) {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "buySellAdminEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean buySellAdminEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                            @Qualifier("buySellAdminDataSource") final DataSource dataSource) {

        final HashMap<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.wosarch.buysell.admin")
                .build();
    }

    @Bean(name = "buySellAdminTransactionManager")
    PlatformTransactionManager buySellAdminTransactionManager(
            @Qualifier("buySellAdminEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

