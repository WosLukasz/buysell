package com.wosarch.buysell.admin.services.synchronizations;

import com.wosarch.buysell.admin.model.exception.ExceptionResources;
import com.wosarch.buysell.admin.model.exception.ResourceProcessingException;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationsService;
import com.wosarch.buysell.admin.repositories.synchronizations.SynchronizationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynchronizationsServiceImpl implements SynchronizationsService {

    Logger logger = LoggerFactory.getLogger(SynchronizationsServiceImpl.class);

    @Autowired
    private SynchronizationsRepository synchronizationsRepository;

    @Override
    public List<SynchronizationItem> findByCodeAndStatus(String code, String status) {
        try {
            return synchronizationsRepository.findByCodeAndStatus(code, status);
        } catch (Exception e) {
            logger.error("Fetching synchronizations failed.");
            throw new ResourceProcessingException(ExceptionResources.SYNCHRONIZATIONS, code, "Fetching synchronizations failed.");
        }
    }

    @Override
    public SynchronizationItem save(SynchronizationItem synchronizationItem) {
        try {
            return synchronizationsRepository.save(synchronizationItem);
        } catch (Exception e) {
            logger.error("Saving synchronizations failed.");
            throw new ResourceProcessingException(ExceptionResources.SYNCHRONIZATIONS, synchronizationItem.getCode(), "Saving synchronizations failed.");
        }
    }
}
