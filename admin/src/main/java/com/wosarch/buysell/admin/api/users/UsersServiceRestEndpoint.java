package com.wosarch.buysell.admin.api.users;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.admin.model.exception.ErrorResponse;
import com.wosarch.buysell.admin.model.users.User;
import com.wosarch.buysell.admin.model.users.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "REST APIs for Users managing for buysell",
        description = "REST APIs for Users managing for buysell"
)
@RestController
@RequestMapping("/admin/users")
public class UsersServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(UsersServiceRestEndpoint.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestContextService requestContextService;

    @Operation(
            summary = "Fetch Current user REST API",
            description = "REST API to fetch Currently logged in user info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/current")
    public ResponseEntity<User> getCurrentUser() {
        logger.debug("Getting current user");

        return new ResponseEntity<>(usersService.getCurrentUser(), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Current user roles REST API",
            description = "REST API to fetch Currently logged in user roles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/current/roles")
    public ResponseEntity<List<String>> getCurrentUserRoles() {
        logger.debug("Getting current user's roles");

        return new ResponseEntity<>(requestContextService.getCurrentUserRoles(), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Current user rights REST API",
            description = "REST API to fetch Currently logged in user rights"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/current/rights")
    public ResponseEntity<List<String>> getCurrentUseRights() {
        logger.debug("Getting current user's rights");

        return new ResponseEntity<>(requestContextService.getCurrentUserRights(), HttpStatus.OK);
    }

}