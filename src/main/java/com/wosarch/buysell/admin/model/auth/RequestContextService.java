package com.wosarch.buysell.admin.model.auth;

import java.util.List;

public interface RequestContextService {

    boolean currentUserHasAssignedRole(String role);

    boolean currentUserHasAssignedAnyRole(List<String> roles);

    boolean currentUserHasAssignedRight(String right);

    boolean currentUserHasAssignedAnyRight(List<String>  rights);

    List<String> getCurrentUserRoles();

    List<String> getCurrentUserRights();

}
