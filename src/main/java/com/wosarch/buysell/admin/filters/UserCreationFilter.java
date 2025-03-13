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
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class UserCreationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(UserCreationFilter.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestContextService requestContextService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestContextService.currentUserLoggedIn()) {
            logger.info("Start creating new user profile");
            Authentication authentication = requestContextService.getCurrentUserContext();
            usersService.createLocalUserIfNotExists(authentication.getName());
            logger.info("New user profile created");
        }

        filterChain.doFilter(request, response);
    }
}