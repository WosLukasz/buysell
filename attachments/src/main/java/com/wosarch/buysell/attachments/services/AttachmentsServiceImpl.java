package com.wosarch.buysell.attachments.services;

import com.wosarch.buysell.attachments.model.attachments.AttachmentSaveRequest;
import com.wosarch.buysell.attachments.config.AppConfig;
import com.wosarch.buysell.attachments.model.attachments.Attachment;
import com.wosarch.buysell.attachments.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.attachments.model.attachments.AttachmentsService;
import com.wosarch.buysell.attachments.model.exception.ExceptionResources;
import com.wosarch.buysell.attachments.model.exception.ResourceProcessingException;
import com.wosarch.buysell.attachments.model.s3client.S3ClientService;
import com.wosarch.buysell.attachments.utils.CommonUtils;
import io.micrometer.common.util.StringUtils;
import io.minio.ObjectWriteResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentsServiceImpl implements AttachmentsService {

    Logger logger = LoggerFactory.getLogger(AttachmentsServiceImpl.class);

    private static final String FILENAME_DATE_PATTERN = "yyyy_MM_dd_HH_mm_ss";
    private static final String DAY_PATTERN = "yyyy_MM_dd";
    private static final Integer FILENAME_RANDOM_PART_LENGTH = 7;

    @Autowired
    private S3ClientService s3ClientService;

    @Autowired
    private AppConfig appConfig;

    @Override
    public Attachment saveAttachment(AttachmentSaveRequest request) throws IOException {
        String path = preparePathForFile(prepareFileName(), request.getContext());

        return saveAttachment(path, request);
    }

    @Override
    public Attachment saveAttachment(String path, AttachmentSaveRequest file) throws IOException {
        try {
            InputStream content = file.getContent().getInputStream();
            String contentType = file.getContent().getContentType();
            String originalFilename = file.getContent().getOriginalFilename();
            Attachment attachment = saveAttachment(path, originalFilename, contentType, content);

            return attachment;
        } catch (Exception e) {
            logger.error("Error during attachment saving {}", path, e);
            throw e;
        }
    }

    @Override
    public Attachment saveAttachment(String path, String originalFileName, String contentType, InputStream content) throws ResourceProcessingException {
        try {
            ObjectWriteResponse saveResponse = s3ClientService.saveObject(appConfig.getMinioDefaultBucket(), path, contentType, content);

            Attachment attachment = new Attachment();
            attachment.setContentType(contentType);
            attachment.setOriginalFilename(originalFileName);
            attachment.setPath(path);
            attachment.setEtag(saveResponse.etag());

            return attachment;
        } catch (ResourceProcessingException e) {
            logger.error("Error during attachment saving {}", path, e);
            throw e;
        }
    }

    @Override
    public AttachmentWithContent getAttachmentWithContent(Attachment attachments) throws ResourceProcessingException {
        AttachmentWithContent attachmentWithContent = new AttachmentWithContent(attachments);
        InputStream inputStream = s3ClientService.getObjectByPath(appConfig.getMinioDefaultBucket(), attachments.getPath());
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            attachmentWithContent.setContent(content);
        } catch (IOException e) {
            logger.error("Converting files content failed.");
            throw new ResourceProcessingException(ExceptionResources.OBJECT, attachments.getPath(), "Converting files content failed.");
        }

        return attachmentWithContent;
    }

    @Override
    public AttachmentWithContent getAttachmentWithContent(String path) throws ResourceProcessingException {
        AttachmentWithContent attachmentWithContent = new AttachmentWithContent();
        InputStream inputStream = s3ClientService.getObjectByPath(appConfig.getMinioDefaultBucket(), path);
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            attachmentWithContent.setPath(path);
            attachmentWithContent.setContent(content);
        } catch (IOException e) {
            logger.error("Converting files content failed.");
            throw new ResourceProcessingException(ExceptionResources.OBJECT, path, "Converting files content failed.");
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
            logger.warn("Attachment with etag {} was not removed. Path is empty", attachment.getEtag());
            throw new ResourceProcessingException(ExceptionResources.OBJECT, attachment.getEtag(), "Path is empty.");
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

    private String prepareFileName() {
        String datePart = CommonUtils.getDateAsString(new Date(), FILENAME_DATE_PATTERN);
        String randomPart = CommonUtils.generateRandomString(FILENAME_RANDOM_PART_LENGTH);

        return String.format("%s_%s", randomPart, datePart);
    }

    private String preparePathForFile(String fileName, String context) {
        String dayDirectory = CommonUtils.getDateAsString(new Date(), DAY_PATTERN);

        return String.format("%s/%s/%s", context, dayDirectory, fileName);
    }
}