package com.wosarch.buysell.model.common;

import lombok.Data;

import java.util.Date;

@Data
public class MongoObject {

    private Integer version;
    private Date creationDate;
    private String createdBy;
    private Date modificationDate;
    private String modifiedBy;
}
