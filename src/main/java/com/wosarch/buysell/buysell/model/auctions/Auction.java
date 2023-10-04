package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.buysell.model.common.ContactInformation;
import com.wosarch.buysell.buysell.model.common.MongoObject;
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
@Document(Auction.COLLECTION_NAME)
public class Auction extends MongoObject {

    @Transient
    public static final String SEQUENCE_NAME = "auctions";
    @Transient
    public static final String COLLECTION_NAME = "auctions";

    @MongoId
    private ObjectId mongoId;

    private String signature;

    private String title;

    private String description;

    private String category;

    private AuctionStatus status;

    private List<Attachment> attachments;

    private String location;

    private List<ContactInformation> contactInformation;

    private List<AuctionReport> reports;

    private String ownerId;

    private AuctionFinishReason finishReason;


    @UtilityClass
    public static class Fields {
        public static final String SIGNATURE = "signature";
        public static final String TITLE = "title";
        public static final String STATUS = "status";
        public static final String FINISH_REASON = "finishReason";
    }
}
