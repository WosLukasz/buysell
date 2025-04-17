package com.wosarch.buysell.admin.repositories.patches;

import com.wosarch.buysell.admin.model.patches.PatchItem;
import com.wosarch.buysell.admin.model.patches.PatchStatus;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JaversSpringDataAuditable
public interface PatchesRepository extends CrudRepository<PatchItem, String>, TemplatePatchesRepository {

    @Query(value = "SELECT patch.id FROM patches AS patch WHERE patch.status=:#{#status.name()}", nativeQuery = true)
    List<String> getPatchesIdsByStatus(@Param("status") PatchStatus status);

}
