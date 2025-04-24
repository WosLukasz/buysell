package com.wosarch.buysell.admin.services.patches;

import com.wosarch.buysell.admin.model.exception.ExceptionResources;
import com.wosarch.buysell.admin.model.exception.ResourceProcessingException;
import com.wosarch.buysell.admin.model.patches.PatchItem;
import com.wosarch.buysell.admin.model.patches.PatchStatus;
import com.wosarch.buysell.admin.model.patches.PatchesService;
import com.wosarch.buysell.admin.repositories.patches.PatchesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatchesServiceImpl implements PatchesService {

    Logger logger = LoggerFactory.getLogger(PatchesServiceImpl.class);

    @Autowired
    private PatchesRepository patchesRepository;

    @Override
    public List<String> getPatchesIdsByStatus(PatchStatus status) {
        try {
            return patchesRepository.getPatchesIdsByStatus(status);
        } catch (Exception e) {
            logger.error("Fetching patch failed.");
            throw new ResourceProcessingException(ExceptionResources.PATCHES, status.name(), "Fetching patch failed.");
        }
    }

    @Override
    public PatchItem save(PatchItem patchItem) {
        try {
            return patchesRepository.save(patchItem);
        } catch (Exception e) {
            logger.error("Saving patch failed.");
            throw new ResourceProcessingException(ExceptionResources.PATCHES, patchItem.getId(), "Saving patch failed.");
        }
    }
}
