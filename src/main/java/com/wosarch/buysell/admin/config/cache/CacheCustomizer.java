package com.wosarch.buysell.admin.config.cache;

import com.wosarch.buysell.admin.model.cache.BuysellCaches;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheCustomizer
        implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(List.of(
                BuysellCaches.ROLE.getCode(),
                BuysellCaches.ROLES.getCode()));
    }
}