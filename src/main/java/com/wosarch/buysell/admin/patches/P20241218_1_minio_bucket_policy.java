package com.wosarch.buysell.admin.patches;

import com.wosarch.buysell.admin.model.patches.Patch;
import com.wosarch.buysell.common.model.s3client.S3ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P20241218_1_minio_bucket_policy implements Patch {

    Logger logger = LoggerFactory.getLogger(P20241218_1_minio_bucket_policy.class);

    private static final String POLICY = "{    \"Version\": \"2012-10-17\",    \"Statement\": [        {            \"Effect\": \"Allow\",            \"Principal\": {                \"AWS\": [                    \"*\"                ]            },            \"Action\": [                \"s3:GetBucketLocation\",                \"s3:ListBucket\"            ],            \"Resource\": [                \"arn:aws:s3:::buysell\"            ]        },        {            \"Effect\": \"Allow\",            \"Principal\": {                \"AWS\": [                    \"*\"                ]            },            \"Action\": [                \"s3:GetObject\"            ],            \"Resource\": [                \"arn:aws:s3:::buysell/*\"            ]        }    ]}";

    @Autowired
    S3ClientService s3ClientService;

    @Override
    public String getPatchId() {
        return "P20241218_1_minio_bucket_policy";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        s3ClientService.setBucketPolicy("buysell", POLICY);

        logger.info("[{}] Finished...", getPatchId());
    }
}