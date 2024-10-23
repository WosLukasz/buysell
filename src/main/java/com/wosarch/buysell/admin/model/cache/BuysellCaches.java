package com.wosarch.buysell.admin.model.cache;

public enum BuysellCaches {
    ROLE("role"),
    ROLES("roles");

    private String code;

    BuysellCaches(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
