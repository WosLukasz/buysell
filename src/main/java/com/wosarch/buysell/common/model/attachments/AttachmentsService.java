package com.wosarch.buysell.common.model.attachments;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AttachmentsService {

    Attachment saveAttachment(String path, MultipartFile multipartFile);

    Attachment saveAttachment(String path, String originalFileName, String contentType, InputStream content);

    AttachmentWithContent getAttachmentWithContent(Attachment attachment) throws IOException;

    List<AttachmentWithContent> getAttachmentsWithContent(List<Attachment> attachments);

    void removeAttachment(Attachment attachment);

    void removeAttachments(List<Attachment> attachment);
}
