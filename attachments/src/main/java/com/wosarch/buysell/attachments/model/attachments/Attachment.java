package com.wosarch.buysell.attachments.model.attachments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    private String etag;

    private String path;

    private String originalFilename;

    private String contentType;

    public Attachment(Attachment attachment) {
        this.etag = attachment.getEtag();
        this.path = attachment.getPath();
        this.originalFilename = attachment.getOriginalFilename();
        this.contentType = attachment.getContentType();
    }
}
