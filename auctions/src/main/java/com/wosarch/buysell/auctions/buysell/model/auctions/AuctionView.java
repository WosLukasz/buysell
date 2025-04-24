package com.wosarch.buysell.auctions.buysell.model.auctions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(AuctionView.COLLECTION_NAME)
public class AuctionView {

    @Transient
    public static final String COLLECTION_NAME = "views";

    @MongoId
    private ObjectId mongoId;

    private String auctionSignature;

    private List<String> views;

    @UtilityClass
    public static class Fields {
        public static final String AUCTION_SIGNATURE = "auctionSignature";
        public static final String VIEWS = "views";
    }
}
