package com.wosarch.buysell.buysell.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "buySellEntityManagerFactory",
        transactionManagerRef = "buySellTransactionManager",
        basePackages = "com.wosarch.buysell.buysell.repositories.posgresql")
@EnableTransactionManagement
@EnableJpaAuditing
public class BuySellDatabaseConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "buySellDataSource")
    @ConfigurationProperties(prefix = "spring.buysell.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "buySellEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean buySellEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                   @Qualifier("buySellDataSource") DataSource dataSource) {

        final HashMap<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.wosarch.buysell.buysell")
                .build();
    }

    @Primary
    @Bean(name = "buySellTransactionManager")
    PlatformTransactionManager buySellETransactionManager(@Qualifier("buySellEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}