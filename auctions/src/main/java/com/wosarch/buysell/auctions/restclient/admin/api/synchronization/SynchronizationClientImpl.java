package com.wosarch.buysell.auctions.restclient.admin.api.synchronization;

import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynchronizationClientImpl implements SynchronizationClient {

    @Override
    public SynchronizationItem saveSynchronization(SynchronizationItem synchronizationItem) {
        return null; // add to queue
    }

    @Override
    public List<SynchronizationItem> findByCodeAndStatus(String code, String name) {
        return List.of(); // get immediately
    }
}
