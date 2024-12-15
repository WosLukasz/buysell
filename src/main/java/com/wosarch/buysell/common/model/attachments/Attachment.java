package com.wosarch.buysell.common.model.attachments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment implements Serializable {
    private String id;
    private String path;
    private String originalFilename;
    private String contentType;
    private boolean main;
    private Integer order;

    public Attachment(Attachment attachment) {
        this.id = attachment.getId();
        this.path = attachment.getPath();
        this.originalFilename = attachment.getOriginalFilename();
        this.contentType = attachment.getContentType();
        this.main = attachment.isMain();
        this.order = attachment.getOrder();
    }
}
