package com.wosarch.buysell.admin.repositories.synchronizations;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SynchronizationsRepository extends MongoRepository<SynchronizationItem, String>, TemplateSynchronizationsRepository {

    @Query(value = "{ 'code' : ?0, 'status' : ?1 }")
    List<SynchronizationItem> findByCodeAndStatus(String code, String status);
}
