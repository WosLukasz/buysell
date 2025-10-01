package com.wosarch.buysell.auctions.buysell.api.emails;

//TODO: To remove. Only for test

import com.wosarch.buysell.auctions.common.model.exception.BuysellException;
import com.wosarch.buysell.auctions.common.services.emails.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/emails")
public class EmailsRestService {

    Logger logger = LoggerFactory.getLogger(EmailsRestService.class);

    @Autowired
    private EmailService emailService;

    @PreAuthorize("permitAll()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> get(@RequestParam("recipient") String recipient) throws BuysellException {
        emailService.send(recipient, "test", "test1");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}