package com.wosarch.buysell.auctions.buysell.model.common;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;

@Data
public class MongoObject implements Serializable {

    @Id // Required by ES
    @MongoId
    private String mongoId; // String type because of ES
    private Long version;
    private Date creationDate;
    private String createdBy;
    private Date modificationDate;
    private String modifiedBy;

    @UtilityClass
    public static class Fields {
        public static final String OBJECT_ID = "_id";
    }
}
