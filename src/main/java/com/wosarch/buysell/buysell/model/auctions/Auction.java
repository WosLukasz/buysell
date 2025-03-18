package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.enums.AuctionFinishReason;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.model.common.Amount;
import com.wosarch.buysell.buysell.model.attachments.Attachment;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Auction.ENTITY_NAME, indexes = {
        @Index(name = "auction_signature", columnList = "signature", unique = true)
})
@org.springframework.data.elasticsearch.annotations.Document(indexName = Auction.ENTITY_NAME)
@Mapping(mappingPath = Auction.ELASTIC_SEARCH_MAPPING)
@Setting(settingPath = Auction.ELASTIC_SEARCH_SETTINGS)
public class Auction extends DatabaseObject {

    @Transient
    public static final String SEQUENCE_NAME = "auctions_seq";
    @Transient
    public static final String ENTITY_NAME = "auctions";
    @Transient
    public static final String ELASTIC_SEARCH_MAPPING = "mappings/auctions.json";
    @Transient
    public static final String ELASTIC_SEARCH_SETTINGS = "mappings/auctions_settings.json";

    private String signature;

    private String title;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "price_id")
    private Amount price;

    private String description;

    private String category; // should I add associacion ? Maybe later

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private Boolean toCheckManually;

    private Date statusChangeDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "auction_id")
    private List<Attachment> attachments;

    private Long sellerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "auction_id")
    private List<AuctionReport> reports;

    private String ownerId;

    @Enumerated(EnumType.STRING)
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
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY = "category";
        public static final String PRICE = "price";
    }
}
