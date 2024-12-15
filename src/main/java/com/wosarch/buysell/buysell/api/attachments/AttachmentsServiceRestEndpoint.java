package com.wosarch.buysell.buysell.api.attachments;

import com.wosarch.buysell.buysell.api.auctions.AuctionsServiceRestEndpoint;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentSaveRequest;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attachments")
public class AttachmentsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsServiceRestEndpoint.class);

    @Autowired
    AttachmentsService attachmentsService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Attachment> addAttachment(@ModelAttribute @Valid AttachmentSaveRequest request) {
        logger.debug("Adding attachment");

        return new ResponseEntity<>(attachmentsService.saveAttachment(request), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> removeAttachment(@PathVariable Attachment attachment) {
        logger.debug("Removing attachments with id {}", attachment.getId());

        attachmentsService.removeAttachment(attachment);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
