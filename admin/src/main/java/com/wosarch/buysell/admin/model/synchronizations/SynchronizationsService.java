package com.wosarch.buysell.admin.model.synchronizations;

import java.util.List;

public interface SynchronizationsService {

    List<SynchronizationItem> findByCodeAndStatus(String code, String status);

    SynchronizationItem save(SynchronizationItem synchronizationItem);

}
