package com.wosarch.buysell.admin.model.users;

import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User extends MongoObject {
    @Transient
    public static final String SEQUENCE_NAME = "users";

    @MongoId
    private ObjectId mongoId;

    private String id;

    private String name;
}
