package com.wosarch.buysell.auctions.buysell.repositories.mongo.views;

import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionView;
import com.wosarch.buysell.auctions.buysell.repositories.mongo.BuysellRepository;
import org.bson.Document;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Repository
public class TemplateAuctionsViewsRepositoryImpl extends BuysellRepository implements TemplateAuctionsViewsRepository {

    private static final String COUNT_ALIAS = "count";
    private static final Integer DEFAULT_COUNT_VALUE = 0;

    @Override
    public Integer getViews(String auctionSignature) {
        AggregationOperation match = Aggregation.match(Criteria.where(AuctionView.Fields.AUCTION_SIGNATURE).is(auctionSignature));
        AggregationOperation projection = Aggregation.project()
                .and(AuctionView.Fields.VIEWS)
                .size()
                .as(COUNT_ALIAS);

        Aggregation agg = Aggregation.newAggregation(match, projection);
        AggregationResults<Document> results = mongoTemplate.aggregate(
                agg, AuctionView.COLLECTION_NAME, Document.class
        );

        if (results == null) {
            return DEFAULT_COUNT_VALUE;
        }

        List<Document> intCount = results.getMappedResults();
        if (CollectionUtils.isEmpty(intCount)) {
            return DEFAULT_COUNT_VALUE;
        }

        return intCount.get(0).get(COUNT_ALIAS, DEFAULT_COUNT_VALUE);
    }

    @Override
    public Integer incrementViews(String signature, String remoteAddress) {
        Query query = new Query();
        query.addCriteria(Criteria.where(AuctionView.Fields.AUCTION_SIGNATURE).is(signature));
        Update update = new Update();
        update.addToSet(AuctionView.Fields.VIEWS, remoteAddress);
        FindAndModifyOptions options = options().returnNew(true).upsert(true);
        AuctionView view = mongoTemplate.findAndModify(query, update, options, AuctionView.class);

        return view.getViews().size();
    }
}
