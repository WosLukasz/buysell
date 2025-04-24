package com.wosarch.buysell.auctions.restclient.attachments.model;

import lombok.Data;

@Data
public class AttachmentWithContent extends Attachment {
    private byte[] content;

    public AttachmentWithContent(Attachment attachment) {
        super(attachment);
    }
}
