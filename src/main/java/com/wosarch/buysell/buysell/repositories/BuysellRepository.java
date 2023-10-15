package com.wosarch.buysell.buysell.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wosarch.buysell.buysell.model.common.MongoObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class BuysellRepository {

    Logger logger = LoggerFactory.getLogger(BuysellRepository.class);

    private static final String SYSTEM_USER = "SYSTEM";
    private static final String MONGO_OBJECT_FIELD = "_id";
    private static final String VERSION_FIELD = "version";
    private static final String HISTORY_COLLECTION_SUFFIX = "_HI";
    private static final String APP_CONTEXT_PACKAGE_PREFIX = "com.wosarch";

    @Autowired
    protected MongoTemplate mongoTemplate;

    public <T extends MongoObject> T versionedSave(T mongoObject, String collection, Class<T> clazz) {
        addAuditData(mongoObject);
        ObjectId mongoId = mongoObject.getMongoId();
        Long version = mongoObject.getVersion();
        if (mongoId != null) {
            Query query = query(where(MONGO_OBJECT_FIELD).is(mongoObject.getMongoId()))
                    .addCriteria(where(VERSION_FIELD).is(version));

            mongoObject.setVersion(mongoObject.getVersion() + 1);

            T response = mongoTemplate.findAndReplace(query, mongoObject);
            handleOptimisticLocking(response, collection, mongoObject);
            saveHistoryDocument(response, collection);

            return response;
        } else {
            mongoObject.setVersion(1l);
            T response = mongoTemplate.insert(mongoObject);
            saveHistoryDocument(response, collection);

            return response;
        }
    }

    private <T extends MongoObject> void handleOptimisticLocking(T response, String collection, T mongoObject) {
        if (response == null) {
            logger.error("Cannot save object in collection {} with id {}, version {}", collection, mongoObject.getMongoId(), mongoObject.getVersion());
            throw new OptimisticLockingFailureException("OptimisticLocking");
        }
    }

    private <T extends MongoObject> void addAuditData(T mongoObject) {
        Date now = new Date();
        if (mongoObject.getCreationDate() == null) {
            mongoObject.setCreationDate(now);
            mongoObject.setCreatedBy(SYSTEM_USER); // To change when we will have user context
        }

        mongoObject.setModificationDate(now);
        mongoObject.setModifiedBy(SYSTEM_USER); // To change when we will have user context
    }

    private <T extends MongoObject> void saveHistoryDocument(T mongoObject, String collection) {
        String historyCollection = String.format("%s%s", collection, HISTORY_COLLECTION_SUFFIX);
        Document historyDocument = prepareHistoryDocument(mongoObject);

        mongoTemplate.getCollection(historyCollection).insertOne(historyDocument);
    }

    private <T extends MongoObject> Document prepareHistoryDocument(T mongoObject) {
        Document document = new Document();
        document.put("document", toDocument(mongoObject));
        document.put("date", mongoObject.getModificationDate());
        document.put("source", getStackTrace());

        return document;
    }

    public static String getStackTrace() {
        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTraceElements = thread.getStackTrace();
        return Arrays.stream(stackTraceElements)
                .filter(stackTraceElement -> stackTraceElement.getClassName().startsWith(APP_CONTEXT_PACKAGE_PREFIX))
                .map(stackTraceElement -> {
                    String className = stackTraceElement.getClassName();
                    String methodName = stackTraceElement.getMethodName();

                    return String.format("%s::%s", className, methodName);
                }).reduce("", (concatenation, element) -> getConcatenation(concatenation, element));
    }

    private static String getConcatenation(String concatenation, String element) {
        if (StringUtils.isEmpty(concatenation)) {
            return element;
        }

        return String.format("%s <- %s", concatenation, element);
    }

    private <T extends MongoObject> Document toDocument(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Document.parse(json);
    }
}