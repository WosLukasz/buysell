package com.wosarch.buysell.admin.api.patches;

import com.wosarch.buysell.admin.model.exception.ErrorResponse;
import com.wosarch.buysell.admin.model.patches.PatchItem;
import com.wosarch.buysell.admin.model.patches.PatchStatus;
import com.wosarch.buysell.admin.model.patches.PatchesService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
        name = "REST APIs for Patches managing for buysell",
        description = "REST APIs for Patches managing for buysell"
)
@RestController
@RequestMapping("/patches")
public class PatchesServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(PatchesServiceRestEndpoint.class);

    @Autowired
    private PatchesService patchesService;

    @Operation(
            summary = "Fetch Patch item by status REST API",
            description = "REST API to fetch Patch item by status"
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
//    @PreAuthorize("isAuthenticated()")
    @RateLimiter(name="getPatchesIdsByStatus") // , fallbackMethod = getPatchesIdsByStatusFallback
    @RequestMapping(path = "/signatures-by-status", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPatchesIdsByStatus(@RequestParam("status") PatchStatus status) {
        return new ResponseEntity<>(patchesService.getPatchesIdsByStatus(status), HttpStatus.OK);
    }

    @Operation(
            summary = "Save Patch item REST API",
            description = "REST API to Save Patch item"
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
    public ResponseEntity<PatchItem> savePatch(@RequestBody PatchItem patchItem) {
        return new ResponseEntity<>(patchesService.save(patchItem), HttpStatus.OK);
    }
}