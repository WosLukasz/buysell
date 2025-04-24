package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsFinish;

import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.auctions.common.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.auctions.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinishAuctionsSynchronizationServiceImpl extends BaseSynchronization {

    @Autowired
    private FinishAuctionService finishAuctionService;

    @Override
    public void run(SynchronizationItem synchronizationItem) {
        try {
            finishAuctionService.processAuctions();
        } catch (Exception e) {
            failed(synchronizationItem, e);
            return;
        }

        success(synchronizationItem);
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.FINISH_AUCTIONS_SYNCHRONIZATION;
    }
}
