package com.wosarch.buysell.buysell.model.auctions.search;

import lombok.Data;

@Data
public class AuctionsSearchRequest {

    private String text;

    private String category;

    private Integer offset;

    private Integer pageSize;

    private String sortBy;

    private String sortOrder;
}
