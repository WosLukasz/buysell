package com.wosarch.buysell.common.services.attachments;

import com.wosarch.buysell.common.model.attachments.AttachmentSaveRequest;
import com.wosarch.buysell.common.config.AppConfig;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import com.wosarch.buysell.common.model.s3client.S3ClientService;
import com.wosarch.buysell.common.utils.CommonUtils;
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
    public Attachment saveAttachment(AttachmentSaveRequest request) {
        String path = preparePathForAuctionFile(prepareFileName(), request.getContext());

        return saveAttachment(path, request);
    }

    @Override
    public Attachment saveAttachment(String path, AttachmentSaveRequest file) {
        try {
            InputStream content = file.getContent().getInputStream();
            String contentType = file.getContent().getContentType();
            String originalFilename = file.getContent().getOriginalFilename();
            Attachment attachment = saveAttachment(path, originalFilename, contentType, content);
            attachment.setMain(file.isMain());
            attachment.setOrder(file.getOrder());

            return attachment;
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

    private String prepareFileName() {
        String datePart = CommonUtils.getDateAsString(new Date(), FILENAME_DATE_PATTERN);
        String randomPart = CommonUtils.generateRandomString(FILENAME_RANDOM_PART_LENGTH);

        return String.format("%s_%s", randomPart, datePart);
    }

    private String preparePathForAuctionFile(String fileName, String context) {
        String dayDirectory = CommonUtils.getDateAsString(new Date(), DAY_PATTERN);

        return String.format("%s/%s/%s", context, dayDirectory, fileName);
    }
}