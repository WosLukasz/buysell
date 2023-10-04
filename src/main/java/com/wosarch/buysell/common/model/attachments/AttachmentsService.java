package com.wosarch.buysell.common.model.attachments;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface AttachmentsService {

    Attachment saveAttachment(String path, MultipartFile multipartFile);

    Attachment saveAttachment(String path, String originalFileName, String contentType, InputStream content);

    Attachment removeAttachment(String name, InputStream content);

    List<Attachment> removeAttachmentsInPath(String path);
}
