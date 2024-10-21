package com.wosarch.buysell.admin.model.users;

import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;

import java.util.List;

public interface UsersService {

    User get(String id);

    User create(UserCreationRequest request, List<String> roles);

    User create(UserCreationRequest request);

    void remove(String userId);

    void assignRoles(String userId, List<String> roles);
}
