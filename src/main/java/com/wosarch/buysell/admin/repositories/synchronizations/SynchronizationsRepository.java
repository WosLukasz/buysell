package com.wosarch.buysell.admin.repositories.synchronizations;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynchronizationsRepository extends MongoRepository<SynchronizationItem, String>, TemplateSynchronizationsRepository { }
