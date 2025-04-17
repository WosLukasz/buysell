package com.wosarch.buysell.admin.api.roles;

import com.wosarch.buysell.admin.model.roles.Role;
import com.wosarch.buysell.admin.model.roles.RolesService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "REST APIs for Roles managing for buysell",
        description = "REST APIs in buysell to FETCH roles buysell"
)
@RestController
@RequestMapping("/roles")
public class RolesServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(RolesServiceRestEndpoint.class);

    @Autowired
    private RolesService rolesService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getAllRoles() {
        logger.debug("Getting all roles");

        return new ResponseEntity<>(rolesService.getAllRoles(), HttpStatus.OK);
    }
}