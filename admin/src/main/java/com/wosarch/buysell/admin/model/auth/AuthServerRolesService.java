package com.wosarch.buysell.admin.model.auth;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface AuthServerRolesService {

    void createRole(String name);

    void removeRole(String name);

    List<RoleRepresentation> findAll();

    boolean roleExists(String name);
}
