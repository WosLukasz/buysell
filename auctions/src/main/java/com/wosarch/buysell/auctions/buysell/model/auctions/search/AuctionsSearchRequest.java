package com.wosarch.buysell.auctions.buysell.model.auctions.search;

import com.wosarch.buysell.auctions.common.model.elasticsearch.ElasticSearchSearchRequest;
import lombok.Data;

@Data
public class AuctionsSearchRequest extends ElasticSearchSearchRequest {

    private String text;

    private String category;

    private Double priceFrom;

    private Double priceTo;
}
