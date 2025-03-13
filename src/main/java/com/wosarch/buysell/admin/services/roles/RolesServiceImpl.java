package com.wosarch.buysell.admin.services.roles;

import com.wosarch.buysell.admin.model.auth.AuthServerRolesService;
import com.wosarch.buysell.admin.model.roles.Role;
import com.wosarch.buysell.admin.model.roles.RolesService;
import com.wosarch.buysell.admin.repositories.roles.RolesRepository;
import com.wosarch.buysell.admin.services.cache.RoleCacheManager;
import com.wosarch.buysell.admin.services.cache.RolesCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AuthServerRolesService authServerRolesService;

    @Autowired
    private RoleCacheManager roleCacheManager;

    @Autowired
    private RolesCacheManager rolesCacheManager;

    @Override
    @Cacheable("role")
    public Optional<Role> getRole(String code) {
        return rolesRepository.findByCode(code);
    }

    @Override
    @Cacheable("roles")
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
        rolesRepository.deleteByCode(code);
    }

    @Override
    public void addRightsToRole(String code, List<String> rightsToAdd) {
        rolesRepository.addRightsToRole(code, rightsToAdd);
        roleCacheManager.cacheEvict(code);
        rolesCacheManager.cacheEvict();
    }

    @Override
    public void removeRightsFromRole(String code, List<String> rightsToRemove) {
        rolesRepository.removeRightsFromRole(code, rightsToRemove);
        roleCacheManager.cacheEvict(code);
        rolesCacheManager.cacheEvict();
    }
}
