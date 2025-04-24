package com.wosarch.buysell.auctions.common.model.synchronizations;

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
public class SynchronizationItem {

    private String id;

    private Date startDate;

    private Date endDate;

    private SynchronizationStatus status;

    private String code;
}
