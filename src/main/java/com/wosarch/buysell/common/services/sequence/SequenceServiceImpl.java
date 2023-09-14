package com.wosarch.buysell.common.services.sequence;

import com.wosarch.buysell.common.model.sequence.Sequence;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public abstract class SequenceServiceImpl implements SequenceService {

    private static final String START_OF_SEQUENCE = "1";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String getNext(String sequenceName) {
        Query query = query(where(Sequence.Fields.NAME).is(sequenceName));
        Update update = new Update().inc(Sequence.Fields.VALUE,1);
        FindAndModifyOptions options = options().returnNew(true).upsert(true);
        Sequence counter = getProperMongoTemplate().findAndModify(query, update, options, Sequence.class);

        return !Objects.isNull(counter) ? String.valueOf(counter.getValue()) : START_OF_SEQUENCE;
    }

    protected abstract MongoTemplate getProperMongoTemplate();
}
