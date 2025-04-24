package com.wosarch.buysell.auctions.common.config.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    Logger logger = LoggerFactory.getLogger(CachingConfig.class);

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

//    @CacheEvict(allEntries = true, value = "user")
//    @Scheduled(fixedDelay = 10 * 60 * 10000 ,  initialDelay = 500)
//    public void cacheNameCacheEvict() {
//        logger.info("Invalidating cache {}", BuysellCaches.CACHE_NAME.getCode());
//    }
}