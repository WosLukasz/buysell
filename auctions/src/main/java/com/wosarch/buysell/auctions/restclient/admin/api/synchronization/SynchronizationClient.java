package com.wosarch.buysell.auctions.restclient.admin.api.synchronization;

import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;

import java.util.List;

public interface SynchronizationClient {

    SynchronizationItem saveSynchronization(SynchronizationItem synchronizationItem);

    List<SynchronizationItem> findByCodeAndStatus(String code, String name);
}
