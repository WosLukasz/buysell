package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsActivation;

import com.wosarch.buysell.auctions.common.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.auctions.common.services.synchronizations.BaseSynchronizationTask;
import com.wosarch.buysell.auctions.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ActivationAuctionsSynchronizationTaskImpl extends BaseSynchronizationTask {

    @Autowired
    private ActivationAuctionsSynchronizationServiceImpl activationAuctionsSynchronizationServiceImpl;

    @Override
    @Scheduled(cron = "${synchronizations.activationAuctions.cron}")
    public void triggerSynchronization() {
        super.triggerSynchronization();
    }

    @Override
    public BaseSynchronization getSynchronizationService() {
        return activationAuctionsSynchronizationServiceImpl;
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.ACTIVATION_AUCTIONS_SYNCHRONIZATION;
    }
}
