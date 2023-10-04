package com.wosarch.buysell.common.model.s3client;

import io.minio.ObjectWriteResponse;

import java.io.InputStream;

public interface S3ClientService {

    void createBucket(String bucket);

    void removeBucket(String bucket);

    ObjectWriteResponse saveObject(String bucket, String path, String contentType, InputStream content) throws RuntimeException;

    void removeObject(String bucket, String objectName);

    InputStream getObjectByName(String bucket, String objectName);

    InputStream getObjectByETag(String bucket, String etag);
}
