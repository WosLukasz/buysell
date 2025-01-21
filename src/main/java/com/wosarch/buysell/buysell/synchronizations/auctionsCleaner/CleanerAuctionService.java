package com.wosarch.buysell.buysell.synchronizations.auctionsCleaner;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.buysell.repositories.mongo.auctions.AuctionsRepository;
import com.wosarch.buysell.buysell.repositories.mongo.historicalauctions.HistoricalAuctionsRepository;
import com.wosarch.buysell.common.model.mongo.MongoDataCollectorConfig;
import com.wosarch.buysell.common.services.mongo.MongoDataCollector;
import com.wosarch.buysell.common.services.parallel.CompletionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CleanerAuctionService {

    Logger logger = LoggerFactory.getLogger(CleanerAuctionService.class);

    private static final Integer BATCH_SIZE = 100;

    private static final Integer PROCESSING_THREADS_POOL_SIZE = 4;

    @Value("${buySell.config.trashHoldDays}")
    private String trashHoldDays;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private HistoricalAuctionsRepository historicalAuctionsRepository;

    @Autowired
    private AuctionAttachmentsService auctionAttachmentsService;

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
            logger.info("No auctions to remove.");
            return;
        }

        try (CompletionIterator<Void> completionIterator = new CompletionIterator<>(PROCESSING_THREADS_POOL_SIZE)) {
            while (!CollectionUtils.isEmpty(dataToProcess)) {
                dataToProcess.forEach(auctionToClear -> {
                    CleanerAuctionJob task = CleanerAuctionJob.builder()
                            .auction(auctionToClear)
                            .auctionsRepository(auctionsRepository)
                            .historicalAuctionsRepository(historicalAuctionsRepository)
                            .auctionAttachmentsService(auctionAttachmentsService)
                            .logger(logger)
                            .build();
                    completionIterator.submit(task);
                });

                completionIterator.waitWithoutResponses();
                dataToProcess = collector.getData();
            }
        } catch (Exception e) {
            logger.error("Error occurred during auctions clearing", e);
            throw e;
        }

        logger.info("All auctions cleared");
    }

    private Criteria prepareFilter() {
        Date trashHoldDate = getTrashHoldDate(new Date());

        return new Criteria().andOperator(
                Criteria.where(Auction.Fields.STATUS).is(AuctionStatus.CLOSED.name()),
                Criteria.where(Auction.Fields.END_DATE).lte(trashHoldDate)
        );
    }

    private Date getTrashHoldDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -Integer.parseInt(trashHoldDays));

        return calendar.getTime();
    }
}
