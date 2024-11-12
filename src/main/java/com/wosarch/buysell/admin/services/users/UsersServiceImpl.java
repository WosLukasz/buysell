package com.wosarch.buysell.admin.services.users;

import com.wosarch.buysell.admin.model.auth.AuthServerUsersService;
import com.wosarch.buysell.admin.model.auth.UserAuthServerRepresentation;
import com.wosarch.buysell.admin.model.roles.SystemRolesCodes;
import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;
import com.wosarch.buysell.admin.repositories.users.UsersRepository;
import com.wosarch.buysell.admin.services.cache.UserCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthServerUsersService authServerUsersService;

    @Autowired
    private UserCacheManager userCacheManager;

    @Override
    public User get(String id) {
        Optional<User> userOptional = usersRepository.findById(id);
        if (userOptional.isEmpty()) {
            logger.error("User with id {} not found", id);
            throw new RuntimeException("User not found");
        }

        return userOptional.get();
    }

    @Override
    public User create(UserCreationRequest request, List<String> roles) {
        try {
            String userId = authServerUsersService.createUser(request, roles);
            User user = new User();
            user.setId(userId);
            user.setFirstName(request.getFirstName());
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            return usersRepository.save(user);
        } catch (Exception e) {
            logger.error("Unable to create user {}", request.getEmail());
            throw e;
        }
    }

    @Override
    public User createLocalUserIfNotExists(String userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        logger.info("User with id {} not found. Creating new one.", userId);
        userCacheManager.cacheEvict(userId);
        UserAuthServerRepresentation userAuthServerRepresentation = authServerUsersService.getUser(userId);
        User user = new User();
        user.setId(userId);
        user.setFirstName(userAuthServerRepresentation.getFirstName());
        user.setName(userAuthServerRepresentation.getName());
        user.setEmail(userAuthServerRepresentation.getEmail());

        return usersRepository.save(user);
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
            userCacheManager.cacheEvict(userId);
        } catch (Exception e) {
            logger.error("Unable to remove user {}", userId);
            throw e;
        }
    }

    @Override
    public void assignRoles(String userId, List<String> roles) {
        authServerUsersService.assignRoles(userId, roles);
        userCacheManager.cacheEvict(userId);
    }
}
