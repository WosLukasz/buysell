package com.wosarch.buysell.admin.patches;

import com.wosarch.buysell.admin.model.patches.Patch;
import com.wosarch.buysell.admin.model.rights.SystemRightsCodes;
import com.wosarch.buysell.admin.model.roles.SystemRolesCodes;
import com.wosarch.buysell.admin.repositories.roles.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class P20240828_1_rights_and_roles implements Patch {

    Logger logger = LoggerFactory.getLogger(P20240828_1_rights_and_roles.class);

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public String getPatchId() {
        return "P20240828_1_rights_and_roles";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        rolesRepository.createRole(SystemRolesCodes.ADMIN.name(), List.of(
                SystemRightsCodes.TEST_RIGHT_2.name(),
                SystemRightsCodes.TEST_RIGHT_3.name())
        );

        rolesRepository.createRole(SystemRolesCodes.SUPERVISOR.name(), List.of(
                SystemRightsCodes.TEST_RIGHT_1.name(),
                SystemRightsCodes.TEST_RIGHT_2.name(),
                SystemRightsCodes.TEST_RIGHT_3.name())
        );

        rolesRepository.createRole(SystemRolesCodes.USER.name(), List.of(SystemRightsCodes.TEST_RIGHT_1.name()));

        logger.info("[{}] Finished...", getPatchId());
    }
}
