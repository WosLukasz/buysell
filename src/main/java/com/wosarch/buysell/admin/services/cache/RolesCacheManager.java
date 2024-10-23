package com.wosarch.buysell.admin.services.cache;

import com.wosarch.buysell.admin.model.cache.BuysellCacheManager;
import com.wosarch.buysell.admin.model.cache.BuysellCaches;
import org.springframework.stereotype.Service;

@Service
public class RolesCacheManager extends BuysellCacheManager {

    public String getCacheCode() {
        return BuysellCaches.ROLES.getCode();
    }
}
