package com.wosarch.buysell.admin.api.users;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import com.wosarch.buysell.admin.model.users.requests.UserCreationRequest;
import jakarta.validation.Valid;
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

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        logger.debug("Getting user with signature {}", id);

        return new ResponseEntity<>(usersService.get(id), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody @Valid UserCreationRequest request) {
        return new ResponseEntity<>(usersService.create(request), HttpStatus.OK);
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