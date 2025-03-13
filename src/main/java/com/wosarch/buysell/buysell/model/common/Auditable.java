package com.wosarch.buysell.buysell.model.common;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    
    @CreatedBy
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDate;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @UtilityClass
    public static class Fields {
        public static final String CREATED_BY = "createdBy";
        public static final String CREATION_DATE = "creationDate";
        public static final String MODIFIED_BY = "modifiedBy";
        public static final String MODIFICATION_DATE = "modificationDate";
    }
}