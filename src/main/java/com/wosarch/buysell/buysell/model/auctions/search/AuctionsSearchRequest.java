package com.wosarch.buysell.buysell.model.auctions.search;

import com.wosarch.buysell.common.model.elasticsearch.ElasticSearchSearchRequest;
import lombok.Data;

@Data
public class AuctionsSearchRequest extends ElasticSearchSearchRequest {

    private String text;

    private String category;

    private Double priceFrom;

    private Double priceTo;
}
