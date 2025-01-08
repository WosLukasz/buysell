package com.wosarch.buysell.admin.services.synchronizations;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationStatus;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationTask;
import com.wosarch.buysell.admin.repositories.synchronizations.SynchronizationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class BaseSynchronizationTask implements SynchronizationTask {

    Logger logger = LoggerFactory.getLogger(BaseSynchronization.class);

    @Autowired
    private SynchronizationsRepository synchronizationsRepository;

    @Override
    public void triggerSynchronization() {
        BaseSynchronization synchronizationService = getSynchronizationService();
        SynchronizationItem item = synchronizationsRepository.save(generateNewSynchronizationItem());
        logger.info("[{}][{}] Starting synchronization", getCode(), item.getId());
        synchronizationService.run(item);
    }

    private SynchronizationItem generateNewSynchronizationItem() {
        SynchronizationItem synchronizationItem = new SynchronizationItem();
        synchronizationItem.setCode(getCode());
        synchronizationItem.setStatus(SynchronizationStatus.IN_PROGRESS);
        synchronizationItem.setStartDate(new Date());

        return synchronizationItem;
    }
}
