package com.wosarch.buysell.attachments.services;

import com.wosarch.buysell.attachments.model.s3client.S3ClientService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyInit {

    Logger logger = LoggerFactory.getLogger(PolicyInit.class);

    /**
     * Only authorized client can upload files to bucket but everybody can fetch data from bucket.
     */
    private static final String POLICY = "{    \"Version\": \"2012-10-17\",    \"Statement\": [        {            \"Effect\": \"Allow\",            \"Principal\": {                \"AWS\": [                    \"*\"                ]            },            \"Action\": [                \"s3:GetBucketLocation\",                \"s3:ListBucket\"            ],            \"Resource\": [                \"arn:aws:s3:::buysell\"            ]        },        {            \"Effect\": \"Allow\",            \"Principal\": {                \"AWS\": [                    \"*\"                ]            },            \"Action\": [                \"s3:GetObject\"            ],            \"Resource\": [                \"arn:aws:s3:::buysell/*\"            ]        }    ]}";

    @Autowired
    S3ClientService s3ClientService;

    @PostConstruct
    public void init() {
        logger.info("Starts initializing policy for bucket buysell...");

        s3ClientService.setBucketPolicy("buysell", POLICY);

        logger.info("Finished initializing policy for bucket buysell...");
    }
}