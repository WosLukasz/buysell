package com.wosarch.buysell.admin.model.cache;

public enum BuysellCaches {
    USERS("users"),
    RIGHTS("rights");

    private String code;

    BuysellCaches(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
