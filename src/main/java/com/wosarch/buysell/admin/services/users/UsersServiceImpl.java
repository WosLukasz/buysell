package com.wosarch.buysell.admin.services.users;

import com.wosarch.buysell.admin.model.auth.AuthServerUsersService;
import com.wosarch.buysell.admin.model.roles.SystemRolesCodes;
import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;
import com.wosarch.buysell.admin.repositories.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private static final String KEYCLOAK_USER_ID_HEADER = "Location";

    Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthServerUsersService authServerUsersService;

    @Override
    public User get(String id) {
        Optional<User> userOptional = usersRepository.findById(id);
        if (userOptional.isEmpty()) {
            logger.error("User with id {} not found", id);
            throw new RuntimeException("User not found");
        }

        return userOptional.get();
    }

    //    TODO: Handle case when user exists
    @Override
    public User create(UserCreationRequest request, List<String> roles) {
        try {
            String userId = authServerUsersService.createUser(request, roles);
            User user = new User();
            user.setId(userId);
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            return usersRepository.save(user);
        } catch (Exception e) {
            logger.error("Unable to create user {}", request.getEmail());
            throw e;
        }
    }

    @Override
    public User create(UserCreationRequest request) {
        return create(request, List.of(SystemRolesCodes.USER.name()));
    }

    @Override
    public void remove(String userId) {
        try {
            authServerUsersService.removeUser(userId);
            usersRepository.deleteById(userId);
        } catch (Exception e) {
            logger.error("Unable to remove user {}", userId);
            throw e;
        }
    }

    @Override
    public void assignRoles(String userId, List<String> roles) {
        authServerUsersService.assignRoles(userId, roles);
    }
}
