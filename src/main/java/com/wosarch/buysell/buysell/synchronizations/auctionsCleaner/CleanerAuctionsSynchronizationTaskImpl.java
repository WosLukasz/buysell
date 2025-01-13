package com.wosarch.buysell.buysell.synchronizations.auctionsCleaner;

import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronizationTask;
import com.wosarch.buysell.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanerAuctionsSynchronizationTaskImpl extends BaseSynchronizationTask {

    @Autowired
    private CleanerAuctionsSynchronizationServiceImpl cleanerAuctionsSynchronizationServiceImpl;

    @Override
    @Scheduled(cron = "${synchronizations.cleanerAuctions.cron}")
    public void triggerSynchronization() {
        super.triggerSynchronization();
    }

    @Override
    public BaseSynchronization getSynchronizationService() {
        return cleanerAuctionsSynchronizationServiceImpl;
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.CLEANER_AUCTIONS_SYNCHRONIZATION;
    }
}
