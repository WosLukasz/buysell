package com.wosarch.buysell.auctions.common.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment implements Serializable {
    private String etag;
    private String path;
    private String originalFilename;
    private String contentType;
    private boolean main;
    private Integer order;

    public Attachment(Attachment attachment) {
        this.etag = attachment.getEtag();
        this.path = attachment.getPath();
        this.originalFilename = attachment.getOriginalFilename();
        this.contentType = attachment.getContentType();
        this.main = attachment.isMain();
        this.order = attachment.getOrder();
    }
}
