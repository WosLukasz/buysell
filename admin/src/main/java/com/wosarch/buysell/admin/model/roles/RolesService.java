package com.wosarch.buysell.admin.model.roles;

import java.util.List;
import java.util.Optional;

public interface RolesService {

    Optional<Role> getRole(String code);

    List<Role> getAllRoles();

    void createRole(String code, List<String> rights);

    void deleteRole(String code);

    void addRightsToRole(String code, List<String> rightsToAdd);

    void removeRightsFromRole(String code, List<String> rightsToRemove);

}
