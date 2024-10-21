package com.wosarch.buysell.admin.services.roles;

import com.wosarch.buysell.admin.model.auth.AuthServerRolesService;
import com.wosarch.buysell.admin.model.roles.Role;
import com.wosarch.buysell.admin.model.roles.RolesService;
import com.wosarch.buysell.admin.repositories.roles.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    AuthServerRolesService authServerRolesService;

    // add cache
    @Override
    public Optional<Role> getRole(String code) {
        return rolesRepository.findById(code);
    }

    // add cache
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public void createRole(String code, List<String> rights) {
        authServerRolesService.createRole(code);
        rolesRepository.createRole(code, rights);
    }

    @Override
    public void deleteRole(String code) {
        authServerRolesService.removeRole(code);
        rolesRepository.deleteRole(code);
    }

    @Override
    public void addRightsToRole(String code, List<String> rightsToAdd) {
        //TODO: TO IMPLEMENT
    }

    @Override
    public void removeRightsFromRole(String code, List<String> rightsToRemove) {
        //TODO: TO IMPLEMENT
    }
}
