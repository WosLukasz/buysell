package com.wosarch.buysell.common.model.attachments;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AttachmentSaveRequest extends Attachment {
    private MultipartFile content;
    private String context;

    public AttachmentSaveRequest(Attachment attachment) {
        super(attachment);
    }
}
