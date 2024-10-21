package com.wosarch.buysell.admin.config.cache;


import com.wosarch.buysell.admin.model.cache.BuysellCaches;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(BuysellCaches.USERS.getCode(), BuysellCaches.RIGHTS.getCode());
    }
}