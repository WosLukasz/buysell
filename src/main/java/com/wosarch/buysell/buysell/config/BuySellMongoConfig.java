package com.wosarch.buysell.buysell.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = "com.wosarch.buysell.buysell.repositories", mongoTemplateRef = "buysellMongoTemplate")
@EnableConfigurationProperties
public class BuySellMongoConfig {

    @Bean(name = "buysellProperties")
    @ConfigurationProperties(prefix = "mongodb.buysell")
    @Primary
    public MongoProperties buysellProperties() {
        return new MongoProperties();
    }

    @Bean(name = "buysellMongoClient")
    public MongoClient mongoClient(@Qualifier("buysellProperties") MongoProperties mongoProperties) throws Exception {
        //  REPLICASET CONNECTION
        //        return MongoClients.create(mongoProperties.getUri());
        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }

    @Primary
    @Bean(name = "buysellMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("buysellMongoClient") MongoClient mongoClient,
            @Qualifier("buysellProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "buysellMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("buysellMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    MongoTransactionManager transactionManager(@Qualifier("buysellMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

}
