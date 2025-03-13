package com.wosarch.buysell.buysell.model.auctions;


import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = AuctionView.ENTITY_NAME)
public class AuctionView extends DatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "views";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ElementCollection // check how it looks like ?
    private List<String> views;

    @UtilityClass
    public static class Fields {
        public static final String AUCTION_SIGNATURE = "auctionSignature";
        public static final String VIEWS = "views";
    }
}
