package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = HistoricalAuction.ENTITY_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalAuction extends DatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "auctions_history";

    private Auction auction;

    private Date moveDate;
}
