package com.wosarch.buysell.auctions.restclient.attachments.client;

import com.wosarch.buysell.auctions.common.config.AppConfig;
import com.wosarch.buysell.auctions.restclient.attachments.model.Attachment;
import com.wosarch.buysell.auctions.restclient.attachments.model.AttachmentClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AttachmentClientImpl implements AttachmentClient {

    private RestClient restClient;

    @Autowired
    private AppConfig appConfig;

    @PostConstruct
    private void initializeClient() {
        // some config
    }

    public Boolean removeAttachment(Attachment attachment) {
        return restClient.delete()
                .uri("")
                .retrieve()
                .body(Boolean.class);
    }

    public Boolean removeAttachments(List<Attachment> attachments) {
        return restClient.delete()
                .uri("")
                .retrieve()
                .body(Boolean.class);
    }

}
