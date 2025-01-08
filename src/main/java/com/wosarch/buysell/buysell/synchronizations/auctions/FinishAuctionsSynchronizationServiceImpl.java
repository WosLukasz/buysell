package com.wosarch.buysell.buysell.synchronizations.auctions;

import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.stereotype.Service;

@Service
public class FinishAuctionsSynchronizationServiceImpl extends BaseSynchronization {

    @Override
    public void run(SynchronizationItem synchronizationItem) {
        try {
            // run logic
            log(synchronizationItem, "Log sth...");
        } catch (Exception e) {
            failed(synchronizationItem);
            throw e;
        }

        success(synchronizationItem);
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.FINISH_AUCTIONS_SYNCHRONIZATION;
    }
}
