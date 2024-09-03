package com.wosarch.buysell.common.model.appstate;

import org.springframework.stereotype.Service;

@Service
public interface AppStateService {

    void setMaintenanceMode();

    void setActiveMode();

    boolean isMaintenanceMode();

    boolean isActiveMode();
}
