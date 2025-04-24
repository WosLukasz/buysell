package com.wosarch.buysell.auctions.common.model.synchronizations;

import com.wosarch.buysell.auctions.common.services.synchronizations.BaseSynchronization;

public interface SynchronizationTask {

    void triggerSynchronization();

    BaseSynchronization getSynchronizationService();

    String getCode();
}
