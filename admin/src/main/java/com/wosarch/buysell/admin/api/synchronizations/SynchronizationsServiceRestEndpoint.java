package com.wosarch.buysell.admin.api.synchronizations;

import com.wosarch.buysell.admin.model.exception.ErrorResponse;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationItem;
import com.wosarch.buysell.admin.model.synchronizations.SynchronizationsService;
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
        name = "REST APIs for Synchronizations managing for buysell",
        description = "REST APIs for Synchronizations managing for buysell"
)
@RestController
@RequestMapping("/admin/synchronizations")
public class SynchronizationsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(SynchronizationsServiceRestEndpoint.class);

    @Autowired
    private SynchronizationsService synchronizationsService;

    @Operation(
            summary = "Fetch Synchronization item by code and status REST API",
            description = "REST API to fetch Synchronization item by code and status"
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
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<SynchronizationItem>> findByCodeAndStatus(@RequestParam("code") String code, @RequestParam("status") String status) {
        return new ResponseEntity<>(synchronizationsService.findByCodeAndStatus(code, status), HttpStatus.OK);
    }

    @Operation(
            summary = "Save Synchronization item REST API",
            description = "REST API to Save Synchronization item"
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SynchronizationItem> saveSynchronization(@RequestBody SynchronizationItem synchronizationItem) {
        return new ResponseEntity<>(synchronizationsService.save(synchronizationItem), HttpStatus.OK);
    }
}