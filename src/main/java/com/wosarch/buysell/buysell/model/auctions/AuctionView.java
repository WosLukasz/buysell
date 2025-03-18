package com.wosarch.buysell.buysell.model.auctions;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = AuctionView.ENTITY_NAME)
@Table(name = AuctionView.ENTITY_NAME, indexes = {
        @Index(name = "views_auction_signature", columnList = "auctionSignature"),
        @Index(name = "views_auction_signature_ip_address", columnList = "auctionSignature, ipAddress", unique = true)
})
public class AuctionView {

    @Transient
    public static final String ENTITY_NAME = "views";

    public AuctionView(String auctionSignature, String ipAddress) {
        this.auctionSignature = auctionSignature;
        this.ipAddress = ipAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String auctionSignature;

    private String ipAddress;

    @UtilityClass
    public static class Fields {
        public static final String AUCTION_SIGNATURE = "auctionSignature";
        public static final String IP_ADDRESS = "ipAddress";
    }
}
