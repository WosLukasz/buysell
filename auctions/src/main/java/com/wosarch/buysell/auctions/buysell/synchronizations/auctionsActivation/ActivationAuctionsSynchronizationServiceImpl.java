package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsActivation;

import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.auctions.common.services.synchronizations.BaseSynchronization;
import com.wosarch.buysell.auctions.buysell.synchronizations.SynchronizationsCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivationAuctionsSynchronizationServiceImpl extends BaseSynchronization {

    @Autowired
    private ActivationAuctionService activationAuctionService;

    @Override
    public void run(SynchronizationItem synchronizationItem) {
        try {
            activationAuctionService.processAuctions();
        } catch (Exception e) {
            failed(synchronizationItem, e);
            return;
        }

        success(synchronizationItem);
    }

    @Override
    public String getCode() {
        return SynchronizationsCodes.ACTIVATION_AUCTIONS_SYNCHRONIZATION;
    }
}
