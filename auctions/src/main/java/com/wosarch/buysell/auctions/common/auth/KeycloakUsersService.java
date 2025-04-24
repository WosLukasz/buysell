package com.wosarch.buysell.auctions.common.auth;

import com.wosarch.buysell.auctions.common.model.auth.AuthServerRolesService;
import com.wosarch.buysell.auctions.common.model.auth.AuthServerUsersService;
import com.wosarch.buysell.auctions.common.model.auth.UserAuthServerRepresentation;
import com.wosarch.buysell.auctions.admin.model.users.requests.UserCreationRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class KeycloakUsersService implements AuthServerUsersService {

    private static final String KEYCLOAK_USER_ID_HEADER = "Location";

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private AuthServerRolesService authServerRolesService;

    @Value("${keycloak.realm}")
    private String realm;

    public UserAuthServerRepresentation getUser(String id) {
        UserResource response = keycloak
                .realm(realm)
                .users()
                .get(id);

        if (Objects.isNull(response)) {
            throw new RuntimeException("No user fount in Keycloak");
        }

        return convertToRepresentation(response.toRepresentation());
    }

    public String createUser(UserCreationRequest request, List<String> roles) {
        CredentialRepresentation password = preparePasswordRepresentation(request.getPassword());
        UserRepresentation userRepresentation = prepareUserRepresentation(request, password);
        Response response = keycloak
                .realm(realm)
                .users()
                .create(userRepresentation);

        Optional<String> idOptional = getUserIdFromResponse(response);
        if (idOptional.isEmpty()) {
            throw new RuntimeException("No id for new user");
        }

        assignRoles(idOptional.get(), roles);

        return idOptional.get();
    }

    public Optional<String> getUserIdFromResponse(Response response) {
        Object idHeader = response.getHeaders().get(KEYCLOAK_USER_ID_HEADER);
        if (Objects.isNull(idHeader)) {
            return Optional.empty();
        }


        List<String> headerList = (ArrayList<String>) idHeader;
        if (CollectionUtils.isEmpty(headerList)) {
            return Optional.empty();
        }

        String location = headerList.getFirst();

        return Optional.of(location.substring(location.lastIndexOf('/') + 1));
    }

    public void removeUser(String userId) {
        keycloak.realm(realm)
                .users()
                .delete(userId);
    }

    public void assignRoles(String userId, List<String> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }

        keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(prepareRoleRepresentations(roles));
    }

    private List<RoleRepresentation> prepareRoleRepresentations(List<String> roles) {
        List<RoleRepresentation> roleRepresentations = authServerRolesService.findAll();

        return roleRepresentations.stream()
                .filter(roleRepresentation -> roles.contains(roleRepresentation.getName()))
                .toList();
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        return credentialRepresentation;
    }

    private UserRepresentation prepareUserRepresentation(
            UserCreationRequest request,
            CredentialRepresentation credentialRepresentation
    ) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(request.getEmail());
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getName());
        newUser.setCredentials(List.of(credentialRepresentation));
        newUser.setEnabled(true);

        return newUser;
    }

    private UserAuthServerRepresentation convertToRepresentation(UserRepresentation userRepresentation) {
        UserAuthServerRepresentation userAuthServerRepresentation = new UserAuthServerRepresentation();
        userAuthServerRepresentation.setEmail(userRepresentation.getEmail());
        userAuthServerRepresentation.setFirstName(userRepresentation.getFirstName());
        userAuthServerRepresentation.setName(userRepresentation.getLastName());

        return userAuthServerRepresentation;
    }

}