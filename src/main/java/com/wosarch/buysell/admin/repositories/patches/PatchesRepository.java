package com.wosarch.buysell.admin.repositories.patches;

import com.wosarch.buysell.admin.model.patches.PatchItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatchesRepository extends MongoRepository<PatchItem, String>, TemplatePatchesRepository { }
