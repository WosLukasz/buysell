package com.wosarch.buysell.admin.model.users;

import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;

public interface UsersService {
    User create(UserCreationRequest request);

    User get(String id);
}
