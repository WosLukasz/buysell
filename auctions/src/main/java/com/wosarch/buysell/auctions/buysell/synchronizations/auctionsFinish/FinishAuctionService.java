package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsFinish;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.auctions.common.model.mongo.MongoDataCollectorConfig;
import com.wosarch.buysell.auctions.common.services.mongo.MongoDataCollector;
import com.wosarch.buysell.auctions.common.services.parallel.CompletionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class FinishAuctionService {

    Logger logger = LoggerFactory.getLogger(FinishAuctionService.class);

    private static final Integer BATCH_SIZE = 100;

    private static final Integer PROCESSING_THREADS_POOL_SIZE = 4;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuctionsService auctionsService;

    public void processAuctions() throws Exception {
        MongoDataCollectorConfig config = MongoDataCollectorConfig.builder()
                .mongoTemplate(mongoTemplate)
                .clazz(Auction.class)
                .filter(prepareFilter())
                .pageSize(BATCH_SIZE)
                .build();

        MongoDataCollector collector = new MongoDataCollector(config);
        List<Auction> dataToProcess = collector.getData();
        if (CollectionUtils.isEmpty(dataToProcess)) {
            logger.info("No auctions to finish.");
            return;
        }

        try (CompletionIterator<Void> completionIterator = new CompletionIterator<>(PROCESSING_THREADS_POOL_SIZE)) {
            while (!CollectionUtils.isEmpty(dataToProcess)) {
                dataToProcess.forEach(auctionToFinish -> {
                    FinishAuctionJob task = FinishAuctionJob.builder()
                            .auction(auctionToFinish)
                            .auctionsService(auctionsService)
                            .logger(logger)
                            .build();
                    completionIterator.submit(task);
                });

                completionIterator.waitWithoutResponses();
                dataToProcess = collector.getData();
            }
        } catch (Exception e) {
            logger.error("Error occurred during auctions finishing", e);
            throw e;
        }

        logger.info("All auctions finished");
    }

    private Criteria prepareFilter() {
        return new Criteria().andOperator(
                Criteria.where(Auction.Fields.STATUS).is(AuctionStatus.ACTIVE.name()),
                Criteria.where(Auction.Fields.EXPIRY_DATE).lte(new Date())
        );
    }
}
