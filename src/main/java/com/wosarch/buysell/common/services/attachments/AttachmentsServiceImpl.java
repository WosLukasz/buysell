package com.wosarch.buysell.common.services.attachments;

import com.wosarch.buysell.common.config.AppConfig;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import com.wosarch.buysell.common.model.s3client.S3ClientService;
import io.micrometer.common.util.StringUtils;
import io.minio.ObjectWriteResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentsServiceImpl implements AttachmentsService {

    Logger logger = LoggerFactory.getLogger(AttachmentsServiceImpl.class);

    @Autowired
    private S3ClientService s3ClientService;

    @Autowired
    private AppConfig appConfig;

    @Override
    public Attachment saveAttachment(String path, MultipartFile multipartFile) {
        try {
            InputStream content = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            String originalFilename = multipartFile.getOriginalFilename();

            return saveAttachment(path, originalFilename, contentType, content);
        } catch (Exception e) {
            logger.error("Error during attachment saving {}", path, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Attachment saveAttachment(String path, String originalFileName, String contentType, InputStream content) {
        try {
            ObjectWriteResponse saveResponse = s3ClientService.saveObject(appConfig.getMinioDefaultBucket(), path, contentType, content);

            Attachment attachment = new Attachment();
            attachment.setContentType(contentType);
            attachment.setOriginalFilename(originalFileName);
            attachment.setPath(path);
            attachment.setId(saveResponse.etag());

            return attachment;
        } catch (Exception e) {
            logger.error("Error during attachment saving {}", path, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public AttachmentWithContent getAttachmentWithContent(Attachment attachments) {
        AttachmentWithContent attachmentWithContent = new AttachmentWithContent(attachments);
        InputStream inputStream = s3ClientService.getObjectByPath(appConfig.getMinioDefaultBucket(), attachments.getPath());
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            attachmentWithContent.setContent(content);
        } catch (IOException e) {
            logger.error("Converting files content failed.");
            throw new RuntimeException(e);
        }

        return attachmentWithContent;
    }

    @Override
    public List<AttachmentWithContent> getAttachmentsWithContent(List<Attachment> attachments) {
        if (CollectionUtils.isEmpty(attachments)) {
            return Collections.emptyList();
        }

        return attachments.stream()
                .map(this::getAttachmentWithContent)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAttachment(Attachment attachment) {
        if (StringUtils.isEmpty(attachment.getPath())) {
            logger.warn("Attachment with id {} was not removed. Path is empty", attachment.getId());
            return;
        }

        s3ClientService.removeObject(appConfig.getMinioDefaultBucket(), attachment.getPath());
    }

    @Override
    public void removeAttachments(List<Attachment> attachments) {
        if (CollectionUtils.isEmpty(attachments)) {
            return;
        }

        attachments.forEach(this::removeAttachment);
    }
}