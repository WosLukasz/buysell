package com.wosarch.buysell.attachments.model.attachments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentWithContent extends Attachment {
    private byte[] content;

    public AttachmentWithContent(Attachment attachment) {
        super(attachment);
    }
}
