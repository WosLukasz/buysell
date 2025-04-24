package com.wosarch.buysell.auctions.common.services.synchronizations;

import com.wosarch.buysell.auctions.common.model.synchronizations.Synchronization;
import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationStatus;
import com.wosarch.buysell.auctions.restclient.admin.api.synchronization.SynchronizationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class BaseSynchronization implements Synchronization {

    Logger logger = LoggerFactory.getLogger(BaseSynchronization.class);

    @Autowired
    private SynchronizationClient synchronizationClient;

    @Override
    public void failed(SynchronizationItem synchronizationItem) {
        logger.error("[{}][{}] Failed to process synchronization", getCode(), synchronizationItem.getId());
        synchronizationItem.setEndDate(new Date());
        synchronizationItem.setStatus(SynchronizationStatus.FAILED);
        synchronizationClient.saveSynchronization(synchronizationItem);
    }

    @Override
    public void failed(SynchronizationItem synchronizationItem, Exception e) {
        logger.error("[{}][{}] Failed to process synchronization:", getCode(), synchronizationItem.getId(), e);
        synchronizationItem.setEndDate(new Date());
        synchronizationItem.setStatus(SynchronizationStatus.FAILED);
        synchronizationClient.saveSynchronization(synchronizationItem);
    }

    @Override
    public void log(SynchronizationItem synchronizationItem, String message) {
        logger.info("[{}][{}] {}", getCode(), synchronizationItem.getId(), message);
    }

    @Override
    public void success(SynchronizationItem synchronizationItem) {
        logger.info("[{}][{}] Successfully processes synchronization", getCode(), synchronizationItem.getId());
        synchronizationItem.setEndDate(new Date());
        synchronizationItem.setStatus(SynchronizationStatus.SUCCESS);
        synchronizationClient.saveSynchronization(synchronizationItem);
    }

}
