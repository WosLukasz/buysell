package com.wosarch.buysell.auctions.common.filters;

import com.wosarch.buysell.auctions.common.model.appstate.AppStateService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

@Component
@Order(1)
public class BlockingEndpointsFilter implements Filter {

    @Autowired
    AppStateService appStateService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (appStateService.isMaintenanceMode()) {
            ((HttpServletResponse) servletResponse).sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service is unavailable");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
