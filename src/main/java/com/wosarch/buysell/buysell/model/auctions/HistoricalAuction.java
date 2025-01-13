package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(HistoricalAuction.COLLECTION_NAME)
public class HistoricalAuction extends MongoObject {

    @Transient
    public static final String SEQUENCE_NAME = "auctions_history";

    @Transient
    public static final String COLLECTION_NAME = "auctions_history";

    private Auction auction;

    private Date moveDate;
}
