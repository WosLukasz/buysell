package com.wosarch.buysell.auctions.common.model.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public abstract class BuysellCacheManager {

    @Autowired
    CacheManager cacheManager;

    public void cacheEvict() {
        cacheManager.getCache(getCacheCode()).clear();
    }

    public void cacheEvict(String key) {
        cacheManager.getCache(getCacheCode()).evict(key);
    }

    protected abstract String getCacheCode();
}
