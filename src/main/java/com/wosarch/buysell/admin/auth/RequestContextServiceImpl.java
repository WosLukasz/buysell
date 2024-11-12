package com.wosarch.buysell.admin.auth;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.admin.model.roles.RolesService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.wosarch.buysell.admin.config.security.KeycloakRoleConverter.SPRING_BOOT_ROLE_PREFIX;

@Service
public class RequestContextServiceImpl implements RequestContextService {

    @Autowired
    RolesService rolesService;

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
        List<String> currentRoles = getCurrentUserRoles();

        return rolesService.getAllRoles().stream()
                .filter(role -> currentRoles.contains(role.getCode()))
                .flatMap(role -> role.getRights().stream())
                .distinct()
                .toList();
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
