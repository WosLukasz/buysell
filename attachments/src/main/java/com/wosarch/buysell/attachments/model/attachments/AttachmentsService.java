package com.wosarch.buysell.attachments.model.attachments;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AttachmentsService {

    Attachment saveAttachment(AttachmentSaveRequest request) throws IOException;

    Attachment saveAttachment(String path, AttachmentSaveRequest file) throws IOException;

    Attachment saveAttachment(String path, String originalFileName, String contentType, InputStream content);

    AttachmentWithContent getAttachmentWithContent(Attachment attachment) throws IOException;

    AttachmentWithContent getAttachmentWithContent(String etag) throws IOException;

    List<AttachmentWithContent> getAttachmentsWithContent(List<Attachment> attachments);

    void removeAttachment(Attachment attachment);

    void removeAttachments(List<Attachment> attachment);
}
