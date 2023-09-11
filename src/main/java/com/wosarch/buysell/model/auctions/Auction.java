package com.wosarch.buysell.model.auctions;

import com.wosarch.buysell.model.common.Attachment;
import com.wosarch.buysell.model.common.ContactInformation;
import com.wosarch.buysell.model.common.MongoObject;
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
@Document("auctions")
public class Auction extends MongoObject {

    @Transient
    public static final String SEQUENCE_NAME = "auctions";

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

    private String ownerId;

    private Long views;

    private AuctionFinishReason finishReason;


    @UtilityClass
    public static class Fields {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String STATUS = "status";
        public static final String FINISH_REASON = "finishReason";
    }
}
