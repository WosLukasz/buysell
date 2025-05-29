package com.wosarch.buysell.attachments.api;

import com.wosarch.buysell.attachments.model.attachments.Attachment;
import com.wosarch.buysell.attachments.model.attachments.AttachmentSaveRequest;
import com.wosarch.buysell.attachments.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.attachments.model.attachments.AttachmentsService;
import com.wosarch.buysell.attachments.model.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(
        name = "REST APIs for Attachments managing for buysell",
        description = "REST APIs in buysell to CREATE, FETCH AND DELETE attachments to bucket buysell"
)
@RestController
@RequestMapping("/attachments")
public class AttachmentsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AttachmentsServiceRestEndpoint.class);

    @Autowired
    AttachmentsService attachmentsService;

    @Operation(
            summary = "Fetch Attachment REST API",
            description = "REST API to fetch Attachment with content"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
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
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AttachmentWithContent> getAttachment(@RequestParam(name = "path") String name) throws IOException {
        logger.debug("Get attachment {}", name);

        return new ResponseEntity<>(attachmentsService.getAttachmentWithContent(name), HttpStatus.OK);
    }

    @Operation(
            summary = "Create Attachment REST API",
            description = "REST API to create Attachment"
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
    @PreAuthorize("isAuthenticated()") // called directly from frontend
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Attachment> addAttachment(@ModelAttribute @Valid AttachmentSaveRequest request) throws IOException {
        logger.debug("Adding new attachment");

        return new ResponseEntity<>(attachmentsService.saveAttachment(request), HttpStatus.OK);
    }

    @Operation(
            summary = "Remove Attachment REST API",
            description = "REST API to remove Attachment with content"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
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
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> removeAttachment(@RequestBody Attachment attachment) {
        logger.debug("Removing attachments with etag {} and path {}", attachment.getEtag(), attachment.getPath());

        attachmentsService.removeAttachment(attachment);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
