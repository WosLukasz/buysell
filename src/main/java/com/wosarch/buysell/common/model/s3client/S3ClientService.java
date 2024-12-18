package com.wosarch.buysell.common.model.s3client;

import io.minio.ObjectWriteResponse;
import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;

public interface S3ClientService {

    void setBucketPolicy(String bucket, String policy);

    void createBucket(String bucket);

    void removeBucket(String bucket);

    ObjectWriteResponse saveObject(String bucket, String path, String contentType, InputStream content) throws RuntimeException;

    void removeObject(String bucket, String path);

    InputStream getObjectByPath(String bucket, String path);

    Iterable<Result<Item>> listObjectsByPath(String bucket, String objectPath);
}
