package com.wosarch.buysell.buysell.model.auctions.search;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import com.wosarch.buysell.common.model.elasticsearch.ElasticSearchSearchRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
@Entity
@Table(name = UserFavourites.ENTITY_NAME)
public class AuctionsSearchRequest extends ElasticSearchSearchRequest {

    @Transient
    public static final String ENTITY_NAME = "auctionSearchRequests";

    private String text;

    private String category;

    private Double priceFrom;

    private Double priceTo;
}
