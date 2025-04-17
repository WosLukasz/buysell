package com.wosarch.buysell.admin.services.appstate;

import com.wosarch.buysell.admin.model.appstate.AppState;
import com.wosarch.buysell.admin.model.appstate.AppStateService;
import org.springframework.beans.factory.annotation.Value;
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
