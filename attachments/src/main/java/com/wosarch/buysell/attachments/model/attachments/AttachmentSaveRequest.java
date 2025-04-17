package com.wosarch.buysell.attachments.model.attachments;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(
        name = "Attachment Save Request",
        description = "Schema to create attachment"
)
public class AttachmentSaveRequest extends Attachment {

    @NotEmpty(message = "Content can not be a null or empty")
    private MultipartFile content;

    @NotEmpty(message = "Context can not be a null or empty")
    private String context;

    public AttachmentSaveRequest(Attachment attachment) {
        super(attachment);
    }
}
