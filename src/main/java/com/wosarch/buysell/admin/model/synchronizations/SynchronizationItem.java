package com.wosarch.buysell.admin.model.synchronizations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(SynchronizationItem.COLLECTION_NAME)
public class SynchronizationItem {
    @Transient
    public static final String COLLECTION_NAME = "synchronizations";

    @Id
    private String id;

    private Date startDate;

    private Date endDate;

    private SynchronizationStatus status;

    private String code;
}
