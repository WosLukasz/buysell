package com.wosarch.buysell.buysell.model.auctions;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = AuctionSignatureSequence.ENTITY_NAME)
@Data
public class AuctionSignatureSequence {

    @Transient
    public static final String ENTITY_NAME = "auctions_sequencer";

    @Transient
    public static final String SEQUENCE_NAME = "auctions_seq";

    @Id
    @SequenceGenerator(name = ENTITY_NAME, sequenceName = SEQUENCE_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ENTITY_NAME)
    private Long signature;

}
