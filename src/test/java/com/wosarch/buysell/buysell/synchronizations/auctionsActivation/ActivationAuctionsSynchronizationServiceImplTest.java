package com.wosarch.buysell.buysell.synchronizations.auctionsActivation;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsService;
import com.wosarch.buysell.buysell.model.common.Amount;
import com.wosarch.buysell.buysell.repositories.mongo.auctions.AuctionsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataMongo
@ExtendWith(SpringExtension.class)
public class ActivationAuctionsSynchronizationServiceImplTest {

    private static final String TEST_SIGNATURE_1 = "TEST_1";
    private static final String TEST_SIGNATURE_2 = "TEST_2";

    @Autowired
    private AuctionsService auctionsService;

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private ActivationAuctionsSynchronizationServiceImpl activationAuctionsSynchronizationService;

    @BeforeAll
    static void setup(@Autowired AuctionsRepository auctionsRepository) {
        auctionsRepository.save(prepareAuction(TEST_SIGNATURE_1));
        auctionsRepository.save(prepareAuction(TEST_SIGNATURE_2));
    }

    @Test
    public void test() {
        SynchronizationItem item = new SynchronizationItem();

        activationAuctionsSynchronizationService.run(item);

        Optional<Auction> auction1 = auctionsRepository.findBySignature(TEST_SIGNATURE_1);
        assertThat(auction1.isPresent()).isEqualTo(true);
        assertThat(auction1.get().getStatus()).isEqualTo(AuctionStatus.ACTIVE);
        Optional<Auction> auction2 = auctionsRepository.findBySignature(TEST_SIGNATURE_2);
        assertThat(auction2.isPresent()).isEqualTo(true);
        assertThat(auction2.get().getStatus()).isEqualTo(AuctionStatus.ACTIVE);
        auctionsRepository.delete(auction1.get());
        auctionsRepository.delete(auction2.get());
    }

    public static Auction prepareAuction(String signature) {
        Auction auction = new Auction();
        auction.setSignature(signature);
        auction.setTitle("Test title");
        auction.setPrice(new Amount());
        auction.setCategory("1");
        auction.setStatus(AuctionStatus.QUEUED);
        auction.setDescription("Test description");
        auction.setStartDate(new Date());

        return auction;
    }
}
