package com.wosarch.buysell.buysell.synchronizations.auctionsCleaner;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CleanerAuctionsSynchronizationServiceImpl extends BaseSynchronization {

    @Autowired
    private CleanerAuctionService cleanerAuctionService;

    @Override
    public void run(SynchronizationItem synchronizationItem) {
        try {
            cleanerAuctionService.processAuctions();
        } catch (Exception e) {
            failed(synchronizationItem, e);
            return;
        }

        success(synchronizationItem);
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.CLEANER_AUCTIONS_SYNCHRONIZATION;
    }
}
