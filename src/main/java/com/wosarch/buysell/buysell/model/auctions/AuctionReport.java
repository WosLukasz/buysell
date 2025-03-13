package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.enums.AuctionReportReason;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = AuctionReport.ENTITY_NAME)
public class AuctionReport extends DatabaseObject {

    public static final String ENTITY_NAME = "reports";

    @Enumerated(EnumType.STRING)
    private AuctionReportReason reason;

    private String message;

    private String userId;
}
