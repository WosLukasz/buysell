package com.wosarch.buysell.admin.patches;

import com.wosarch.buysell.admin.model.patches.Patch;
import com.wosarch.buysell.admin.model.roles.SystemRolesCodes;
import com.wosarch.buysell.admin.model.users.UsersService;
import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class P20241014_1_users implements Patch {

    Logger logger = LoggerFactory.getLogger(P20241014_1_users.class);

    @Autowired
    UsersService usersService;

    @Override
    public String getPatchId() {
        return "P20241014_1_users";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        UserCreationRequest request = new UserCreationRequest();
        request.setFirstName("Robert");
        request.setName("Edler");
        request.setPassword("bobspassword");
        request.setEmail("bob@buysell.com");

        usersService.create(request, List.of(
                SystemRolesCodes.USER.name(),
                SystemRolesCodes.ADMIN.name(),
                SystemRolesCodes.SUPERVISOR.name()
        ));

        logger.info("[{}] Finished...", getPatchId());
    }
}