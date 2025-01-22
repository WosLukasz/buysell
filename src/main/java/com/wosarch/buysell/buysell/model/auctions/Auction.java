package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.common.Amount;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(Auction.COLLECTION_NAME)
@org.springframework.data.elasticsearch.annotations.Document(indexName = Auction.COLLECTION_NAME)
@Mapping(mappingPath = Auction.ELASTIC_SEARCH_MAPPING)
public class Auction extends MongoObject {

    @Transient
    public static final String SEQUENCE_NAME = "auctions";
    @Transient
    public static final String COLLECTION_NAME = "auctions";
    @Transient
    public static final String ELASTIC_SEARCH_MAPPING = "mappings/auctions.json";

    private String signature;

    private String title;

    private Amount price;

    private String description;

    private String category;

    private AuctionStatus status;

    private Boolean toCheckManually;

    private Date statusChangeDate;

    private List<Attachment> attachments;

    private SellerProfile seller;

    private List<AuctionReport> reports;

    private String ownerId;

    private AuctionFinishReason finishReason;

    private Date startDate;

    private Date lastRefreshmentDate;

    private Date expiryDate;

    private Date endDate;

    @UtilityClass
    public static class Fields {
        public static final String SIGNATURE = "signature";
        public static final String TITLE = "title";
        public static final String STATUS = "status";
        public static final String EXPIRY_DATE = "expiryDate";
        public static final String END_DATE = "endDate";
        public static final String FINISH_REASON = "finishReason";
        public static final String CATEGORY = "category";

    }
}
