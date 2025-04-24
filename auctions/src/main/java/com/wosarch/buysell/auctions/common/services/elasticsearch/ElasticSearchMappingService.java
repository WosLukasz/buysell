package com.wosarch.buysell.auctions.common.services.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ElasticSearchMappingService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchMappingService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public InputStream read(final String filePath) {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);

        try {
            return resource.getInputStream();
        } catch (IOException e) {
            logger.error("Error during mapping read {}", e.getMessage(), e);
            return null;
        }
    }

}
