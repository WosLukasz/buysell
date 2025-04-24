package com.wosarch.buysell.auctions.restclient.attachments.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AttachmentSaveRequest {

    private MultipartFile content;

    private String context;

    private String etag;

    private String path;

    private String originalFilename;

    private String contentType;

}
