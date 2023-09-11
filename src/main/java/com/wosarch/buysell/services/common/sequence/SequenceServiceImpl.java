package com.wosarch.buysell.services.common.sequence;

import com.wosarch.buysell.model.common.sequence.Sequence;
import com.wosarch.buysell.model.common.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceServiceImpl implements SequenceService {

    private static final String START_OF_SEQUENCE = "1";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String getNext(String sequenceName) {
        Query query = query(where(Sequence.Fields.NAME).is(sequenceName));
        Update update = new Update().inc(Sequence.Fields.VALUE,1);
        FindAndModifyOptions options = options().returnNew(true).upsert(true);
        Sequence counter = mongoTemplate.findAndModify(query, update, options, Sequence.class);

        return !Objects.isNull(counter) ? String.valueOf(counter.getValue()) : START_OF_SEQUENCE;
    }
}
