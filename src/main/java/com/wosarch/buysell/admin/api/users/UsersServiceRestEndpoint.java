package com.wosarch.buysell.admin.api.users;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(UsersServiceRestEndpoint.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestContextService requestContextService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser() {
        logger.debug("Getting current user");

        return new ResponseEntity<>(usersService.getCurrentUser(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/current/roles")
    public ResponseEntity<List<String>> getCurrentUserRoles() {
        return new ResponseEntity<>(requestContextService.getCurrentUserRoles(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/current/rights")
    public ResponseEntity<List<String>> getCurrentUseRights() {
        return new ResponseEntity<>(requestContextService.getCurrentUserRights(), HttpStatus.OK);
    }

}