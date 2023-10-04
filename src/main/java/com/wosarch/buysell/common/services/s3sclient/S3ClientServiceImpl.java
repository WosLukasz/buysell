package com.wosarch.buysell.common.services.s3sclient;

import com.wosarch.buysell.admin.api.users.UsersServiceRestEndpoint;
import com.wosarch.buysell.common.config.AppConfig;
import com.wosarch.buysell.common.model.s3client.S3ClientService;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Minio.io client More details:
 * https://www.baeldung.com/minio
 * https://min.io/docs/minio/linux/developers/java/API.html
 */
@Service
public class S3ClientServiceImpl implements S3ClientService {

    private static final int MINIMAL_PART_SIZE = 1024 * 1024 * 5;

    Logger logger = LoggerFactory.getLogger(UsersServiceRestEndpoint.class);

    private MinioClient client;

    @Autowired
    private AppConfig appConfig;

    @PostConstruct
    private void initializeClient() {
        client = MinioClient.builder()
                .endpoint(appConfig.getMinioEndpoint())
                .credentials(appConfig.getMinioAccessKey(), appConfig.getMinioSecretKey())
                .build();
    }

    @Override
    public void createBucket(String bucket) {
        MakeBucketArgs bucketCreateArgs = MakeBucketArgs.builder()
                .bucket(bucket)
                .build();

        try {
            client.makeBucket(bucketCreateArgs);
        } catch (Exception e) {
            logger.error("Bucket {} creating failed: ", bucket, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBucket(String bucket) {
        RemoveBucketArgs bucketRemoveArgs = RemoveBucketArgs.builder()
                .bucket(bucket)
                .build();

        try {
            client.removeBucket(bucketRemoveArgs);
        } catch (Exception e) {
            logger.error("Bucket {} removing failed: ", bucket, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectWriteResponse saveObject(String bucket, String objectPath, String contentType, InputStream content) throws RuntimeException {
        PutObjectArgs putArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectPath)
                .stream(content, -1, MINIMAL_PART_SIZE)
                .contentType(contentType)
                .build();

        try {
            logger.debug("Saving file {} in bucket with content type {}", objectPath, bucket, contentType);
            return client.putObject(putArgs);
        } catch (Exception e) {
            logger.error("Object saving failed: [{}, {}]", bucket, objectPath, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObject(String bucket, String objectPath) {
        RemoveObjectArgs objectRemoveArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(objectPath)
                .build();

        try {
            logger.debug("Removing file {} from bucket ", objectPath, bucket);
            client.removeObject(objectRemoveArgs);
        } catch (Exception e) {
            logger.error("Object saving failed: [{}, {}]", bucket, objectPath, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getObjectByName(String bucket, String objectName) {
        GetObjectArgs getArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build();

        try {
            return client.getObject(getArgs);
        } catch (Exception e) {
            logger.error("Object fetching failed: [{}, {}]", bucket, objectName, e);
            return null;
        }
    }

    @Override
    public InputStream getObjectByETag(String bucket, String etag) {
        GetObjectArgs getArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .matchETag(etag)
                .build();

        try {
            return client.getObject(getArgs);
        } catch (Exception e) {
            logger.error("Object fetching failed: [{}, {}]", bucket, etag, e);
            return null;
        }
    }
}
