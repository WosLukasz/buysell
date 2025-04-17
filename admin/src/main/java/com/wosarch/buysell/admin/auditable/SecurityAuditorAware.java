package com.wosarch.buysell.admin.auditable;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<String> {

    private static final String SYSTEM_USER = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> currentUser = getCurrentUser();

        return currentUser.isPresent() ? currentUser : Optional.of(SYSTEM_USER);
    }

    private Optional<String> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}