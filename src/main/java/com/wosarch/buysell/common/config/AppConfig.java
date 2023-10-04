package com.wosarch.buysell.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

    @Value( "${minio.endpoint}" )
    private String minioEndpoint;

    @Value( "${minio.accessKey}" )
    private String minioAccessKey;

    @Value( "${minio.secretKey}" )
    private String minioSecretKey;

    @Value( "${minio.defaultBucket}" )
    private String minioDefaultBucket;
}
