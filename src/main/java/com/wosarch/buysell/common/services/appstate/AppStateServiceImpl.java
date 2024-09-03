package com.wosarch.buysell.common.services.appstate;

import org.springframework.beans.factory.annotation.Value;
import com.wosarch.buysell.common.model.appstate.AppState;
import com.wosarch.buysell.common.model.appstate.AppStateService;
import org.springframework.stereotype.Service;

@Service
public class AppStateServiceImpl implements AppStateService {

    @Value( "${buySell.config.appState}" )
    private AppState appState;

    @Override
    public void setMaintenanceMode() {
        this.appState = AppState.MAINTENANCE;
    }

    @Override
    public void setActiveMode() {
        this.appState = AppState.ACTIVE;
    }

    @Override
    public boolean isMaintenanceMode() {
        return  AppState.MAINTENANCE.equals(this.appState);
    }

    @Override
    public boolean isActiveMode() {
        return AppState.ACTIVE.equals(this.appState);
    }
}
