package com.wosarch.buysell.auctions.common.model.attachment;

import lombok.Data;

@Data
public class AttachmentWithContent extends Attachment {
    private byte[] content;

    public AttachmentWithContent(Attachment attachment) {
        super(attachment);
    }
}
