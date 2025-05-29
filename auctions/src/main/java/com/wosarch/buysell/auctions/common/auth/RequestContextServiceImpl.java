package com.wosarch.buysell.auctions.common.auth;

import com.wosarch.buysell.auctions.common.model.auth.RequestContextService;
import com.wosarch.buysell.auctions.restclient.admin.api.roles.RolesClient;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.wosarch.buysell.auctions.common.config.security.KeycloakRoleConverter.SPRING_BOOT_ROLE_PREFIX;

@Service
public class RequestContextServiceImpl implements RequestContextService {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private RolesClient rolesClient;

    @Override
    public boolean currentUserLoggedIn() {
        Authentication authentication = getCurrentUserContext();

        return null != authentication && !(ANONYMOUS_USER).equals(authentication.getName());
    }

    @Override
    public boolean currentUserHasAssignedRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }

        List<String> roles = getCurrentUserRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        return roles.contains(role);
    }

    @Override
    public boolean currentUserHasAssignedAnyRole(List<String> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        List<String> currentRoles = getCurrentUserRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        return roles.stream().anyMatch(currentRoles::contains);
    }

    @Override
    public boolean currentUserHasAssignedRight(String right) {
        if (StringUtils.isEmpty(right)) {
            return false;
        }

        List<String> currentRights = getCurrentUserRights();
        if (CollectionUtils.isEmpty(currentRights)) {
            return false;
        }

        return currentRights.contains(right);
    }

    @Override
    public boolean currentUserHasAssignedAnyRight(List<String> rights) {
        if (CollectionUtils.isEmpty(rights)) {
            return false;
        }

        List<String> currentRights = getCurrentUserRights();
        if (CollectionUtils.isEmpty(currentRights)) {
            return false;
        }

       return rights.stream().anyMatch(currentRights::contains);
    }

    @Override
    public List<String> getCurrentUserRoles() {
        Authentication authentication = getCurrentUserContext();

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.substring(SPRING_BOOT_ROLE_PREFIX.length()))
                .toList();
    }

    @Override
    public List<String> getCurrentUserRights() {
        return rolesClient.getCurrentUserRights();
    }

    @Override
    public Authentication getCurrentUserContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getCurrentUserId() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}
