package com.wosarch.buysell.common.model.attachments;

import lombok.Data;

import java.io.InputStream;

@Data
public class AttachmentWithContent extends Attachment {
    private byte[] content;

    public AttachmentWithContent(Attachment attachment) {
        super(attachment);
    }
}
