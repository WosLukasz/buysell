package com.wosarch.buysell.admin.repositories.patches;

import com.wosarch.buysell.admin.model.patches.PatchItem;
import com.wosarch.buysell.admin.model.patches.PatchStatus;
import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class TemplatePatchesRepositoryImpl extends AdminRepository implements TemplatePatchesRepository {

    Logger logger = LoggerFactory.getLogger(TemplatePatchesRepositoryImpl.class);

    @Override
    public List<String> getPatchesIdsByStatus(PatchStatus status) {
        if (Objects.isNull(status)) {
            return Collections.emptyList();
        }

        Query query = new Query();
        query.addCriteria(Criteria.where(PatchItem.Fields.STATUS).is(status));

        return mongoTemplate.findDistinct(query, PatchItem.Fields.ID, PatchItem.class, String.class);
    }
}
