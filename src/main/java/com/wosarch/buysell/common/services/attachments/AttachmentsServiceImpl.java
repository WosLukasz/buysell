package com.wosarch.buysell.common.services.attachments;

import com.wosarch.buysell.common.config.AppConfig;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import com.wosarch.buysell.common.model.s3client.S3ClientService;
import io.minio.ObjectWriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

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
            logger.error("Error during file saving {}", path, e);
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
            logger.error("Error during file saving {}", path, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Attachment removeAttachment(String name, InputStream content) {
        return null;
    }

    @Override
    public List<Attachment> removeAttachmentsInPath(String path) {
        return null;
    }
}