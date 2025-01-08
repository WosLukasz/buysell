package com.wosarch.buysell.buysell.synchronizations.auctions;

import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronizationTask;
import com.wosarch.buysell.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FinishAuctionsSynchronizationTaskImpl extends BaseSynchronizationTask {

    @Autowired
    private FinishAuctionsSynchronizationServiceImpl finishAuctionsSynchronizationServiceImpl;

    @Override
    @Scheduled(cron = "${synchronizations.finishAuctions.cron}")
    public void triggerSynchronization() {
        super.triggerSynchronization();
    }

    @Override
    public BaseSynchronization getSynchronizationService() {
        return finishAuctionsSynchronizationServiceImpl;
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.FINISH_AUCTIONS_SYNCHRONIZATION;
    }
}
