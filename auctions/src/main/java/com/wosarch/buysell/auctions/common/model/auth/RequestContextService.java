package com.wosarch.buysell.auctions.common.model.auth;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface RequestContextService {

    boolean currentUserLoggedIn();

    boolean currentUserHasAssignedRole(String role);

    boolean currentUserHasAssignedAnyRole(List<String> roles);

    boolean currentUserHasAssignedRight(String right);

    boolean currentUserHasAssignedAnyRight(List<String>  rights);

    List<String> getCurrentUserRoles();

    List<String> getCurrentUserRights();

    Authentication getCurrentUserContext();

    String getCurrentUserId();

}
