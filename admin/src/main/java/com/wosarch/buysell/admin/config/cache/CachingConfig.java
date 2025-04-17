package com.wosarch.buysell.admin.config.cache;

import com.wosarch.buysell.admin.model.cache.BuysellCaches;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class CachingConfig {

    Logger logger = LoggerFactory.getLogger(CachingConfig.class);

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                BuysellCaches.USER.getCode(),
                BuysellCaches.ROLE.getCode(),
                BuysellCaches.ROLES.getCode());
    }

    @CacheEvict(allEntries = true, value = "user")
    @Scheduled(fixedDelay = 10 * 60 * 10000 ,  initialDelay = 500)
    public void userCacheEvict() {
        logger.info("Invalidating cache {}", BuysellCaches.USER.getCode());
    }

    @CacheEvict(allEntries = true, value = "roles")
    @Scheduled(fixedDelay = 10 * 60 * 10000 ,  initialDelay = 500)
    public void rolesCacheEvict() {
        logger.info("Invalidating cache {}", BuysellCaches.ROLES.getCode());
    }

    @CacheEvict(allEntries = true, value = "role")
    @Scheduled(fixedDelay = 10 * 60 * 10000 ,  initialDelay = 500)
    public void roleCacheEvict() {
        logger.info("Invalidating cache {}", BuysellCaches.ROLE.getCode());
    }
}