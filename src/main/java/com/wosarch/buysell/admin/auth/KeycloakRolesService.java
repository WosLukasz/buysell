package com.wosarch.buysell.admin.auth;

import com.wosarch.buysell.admin.model.auth.AuthServerRolesService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakRolesService implements AuthServerRolesService {

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void createRole(String name) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(name);
        keycloak.realm(realm)
                .roles()
                .create(role);
    }

    public void removeRole(String name) {
        keycloak.realm(realm)
                .roles()
                .deleteRole(name);
    }

    public List<RoleRepresentation> findAll() {
        return keycloak
                .realm(realm)
                .roles()
                .list();
    }
}