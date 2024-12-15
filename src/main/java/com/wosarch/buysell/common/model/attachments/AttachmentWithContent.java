package com.wosarch.buysell.common.model.attachments;

import lombok.Data;

@Data
public class AttachmentWithContent extends Attachment {
    private byte[] content;

    public AttachmentWithContent(Attachment attachment) {
        super(attachment);
    }
}
