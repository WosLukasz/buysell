package com.wosarch.buysell.admin.repositories.roles;

import com.wosarch.buysell.admin.model.roles.Role;

import java.util.List;
import java.util.Optional;

public interface TemplateRolesRepository {

    Optional<Role>  findByCode(String code);

    void createRole(String code, List<String> rights);

    void addRightsToRole(String code, List<String> rightsToAdd);

    void removeRightsFromRole(String code, List<String> rightsToRemove);

}