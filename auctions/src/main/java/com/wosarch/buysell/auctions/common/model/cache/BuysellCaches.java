package com.wosarch.buysell.auctions.common.model.cache;

public enum BuysellCaches {
    ;

    private String code;

    BuysellCaches(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
