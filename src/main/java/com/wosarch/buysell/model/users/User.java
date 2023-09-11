package com.wosarch.buysell.model.users;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class User {

    @MongoId
    private ObjectId mongoId;

    private String id;

    private String name;
}
