package com.wosarch.buysell.admin.repositories.synchronizations;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SynchronizationsRepository extends CrudRepository<SynchronizationItem, Long>, TemplateSynchronizationsRepository {

    @Query(value = "SELECT * FROM synchronizations WHERE code = ?1 AND status = ?2", nativeQuery = true)
    List<SynchronizationItem> findByCodeAndStatus(String code, String status);
}
