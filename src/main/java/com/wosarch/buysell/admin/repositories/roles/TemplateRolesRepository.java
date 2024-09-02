package com.wosarch.buysell.admin.repositories.roles;

import java.util.List;

public interface TemplateRolesRepository {

    void createRole(String code, List<String> rights);

    void deleteRole(String code);

    void addRightsToRole(String code, List<String> rightsToAdd);

    void removeRightsFromRole(String code, List<String> rightsToRemove);

}