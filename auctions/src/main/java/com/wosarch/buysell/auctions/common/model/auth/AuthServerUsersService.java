package com.wosarch.buysell.auctions.common.model.auth;

import com.wosarch.buysell.auctions.admin.model.users.requests.UserCreationRequest;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

public interface AuthServerUsersService {

    UserAuthServerRepresentation getUser(String id);

    String createUser(UserCreationRequest request, List<String> roles);

    Optional<String> getUserIdFromResponse(Response response);

    void removeUser(String userId);

    void assignRoles(String userId, List<String> roles);

}
