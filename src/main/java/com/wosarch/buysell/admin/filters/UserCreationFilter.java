package com.wosarch.buysell.admin.filters;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.admin.model.users.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserCreationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(UserCreationFilter.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestContextService requestContextService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = requestContextService.getCurrentUserContext();
        if (authentication.isAuthenticated()) {
            logger.info("Start creating new user profile");
            usersService.createLocalUserIfNotExists(authentication.getName());
            logger.info("New user profile created");
        }

        filterChain.doFilter(request, response);
    }
}