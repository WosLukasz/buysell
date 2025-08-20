package com.wosarch.buysell.auctions.restclient.attachments;

import com.wosarch.buysell.auctions.buysell.model.common.CommunicationException;
import com.wosarch.buysell.auctions.common.config.AppConfig;
import com.wosarch.buysell.auctions.common.model.attachment.Attachment;
import com.wosarch.buysell.auctions.restclient.admin.users.UsersClientImpl;
import com.wosarch.buysell.auctions.restclient.admin.users.UsersFeignClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class AttachmentClientImpl implements AttachmentClient {

    Logger logger = LoggerFactory.getLogger(AttachmentClientImpl.class);

    @Autowired
    private AttachmentFeignClient attachmentFeignClient;

    @Override
    public Boolean removeAttachments(List<Attachment> attachments) {
        try {
            ResponseEntity<Boolean> response = attachmentFeignClient.removeAttachments(attachments);
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during removeAttachments ", e);
            throw e;
        }
    }
}
