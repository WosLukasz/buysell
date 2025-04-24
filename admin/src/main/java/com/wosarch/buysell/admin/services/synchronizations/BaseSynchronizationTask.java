package com.wosarch.buysell.admin.services.synchronizations;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationStatus;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationTask;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public abstract class BaseSynchronizationTask implements SynchronizationTask {

    Logger logger = LoggerFactory.getLogger(BaseSynchronization.class);

    @Autowired
    private SynchronizationsService synchronizationsService;

    @Override
    public void triggerSynchronization() {
        List<SynchronizationItem> synchronizationInProgress = getSynchronizationsInProgress();
        if (!CollectionUtils.isEmpty(synchronizationInProgress)) {
            logger.warn("[{}] Can not start synchronization. Previous still running", getCode());
            return;
        }

        BaseSynchronization synchronizationService = getSynchronizationService();
        SynchronizationItem item = synchronizationsService.save(generateNewSynchronizationItem());
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

    private List<SynchronizationItem> getSynchronizationsInProgress() {
        return synchronizationsService.findByCodeAndStatus(getCode(), SynchronizationStatus.IN_PROGRESS.name());
    }
}
