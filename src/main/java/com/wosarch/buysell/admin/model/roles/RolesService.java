package com.wosarch.buysell.admin.model.roles;

import java.util.List;

public interface RolesService {

    void createRole(String code, List<String> rights);

    void deleteRole(String code);

    void addRightsToRole(String code, List<String> rightsToAdd);

    void removeRightsFromRole(String code, List<String> rightsToRemove);

}
