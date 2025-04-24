package com.wosarch.buysell.auctions.buysell.config;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.wosarch.buysell.buysell.repositories.elastic")
public class BuySellElasticsearchConfig extends ElasticsearchConfiguration {

    @Value( "${elasticSearch.buysell.uri}" )
    private String esUri;

    @Value( "${elasticSearch.buysell.user}" )
    private String user;

    @Value( "${elasticSearch.buysell.password}" )
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(esUri)
//                .usingSsl(buildSSLContext())
                .withBasicAuth(user, password)
                .build();
    }

    @Override
    protected boolean writeTypeHints() {
        return false;
    }

    private static SSLContext buildSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
