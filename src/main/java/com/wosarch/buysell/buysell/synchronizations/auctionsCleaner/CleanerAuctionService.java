package com.wosarch.buysell.buysell.synchronizations.auctionsCleaner;

import co.elastic.clients.util.TriFunction;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionAttachmentsService;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.repositories.elastic.auctions.AuctionsElasticSearchRepository;
import com.wosarch.buysell.buysell.repositories.posgresql.auctions.AuctionsRepository;
import com.wosarch.buysell.common.model.mongo.DataCollectorConfig;
import com.wosarch.buysell.common.services.mongo.DataCollector;
import com.wosarch.buysell.common.services.parallel.CompletionIterator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EntityManager entityManager;

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private AuctionsElasticSearchRepository auctionsElasticSearchRepository;

    @Autowired
    private AuctionAttachmentsService auctionAttachmentsService;

    public void processAuctions() throws Exception {
        DataCollectorConfig config = DataCollectorConfig.builder()
                .entityManager(entityManager)
                .clazz(Auction.class)
                .predicatesProvider(preparePredicates(getTrashHoldDate(new Date())))
                .pageSize(BATCH_SIZE)
                .build();

        DataCollector collector = new DataCollector(config);
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
                            .auctionsElasticSearchRepository(auctionsElasticSearchRepository)
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


    private TriFunction<CriteriaBuilder, CriteriaQuery<?>, Root<?>, List<Predicate>> preparePredicates(Date trashHoldDate) {
        return (CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<?> root) ->
                List.of(
                        criteriaBuilder.equal(root.get(Auction.Fields.STATUS).as(String.class), AuctionStatus.CLOSED.name()),
                        criteriaBuilder.lessThanOrEqualTo(root.get(Auction.Fields.END_DATE), trashHoldDate)
                );
    }

    private Date getTrashHoldDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -Integer.parseInt(trashHoldDays));

        return calendar.getTime();
    }
}
