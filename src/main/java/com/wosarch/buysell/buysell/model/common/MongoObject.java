package com.wosarch.buysell.buysell.model.common;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
public class MongoObject {

    @MongoId
    private ObjectId mongoId;
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
