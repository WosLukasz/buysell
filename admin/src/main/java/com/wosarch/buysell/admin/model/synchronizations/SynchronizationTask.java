package com.wosarch.buysell.admin.model.synchronizations;

import com.wosarch.buysell.admin.services.synchronizations.BaseSynchronization;

public interface SynchronizationTask {

    void triggerSynchronization();

    BaseSynchronization getSynchronizationService();

    String getCode();
}
