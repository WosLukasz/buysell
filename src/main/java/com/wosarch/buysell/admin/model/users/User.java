package com.wosarch.buysell.admin.model.users;

import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(User.COLLECTION_NAME)
public class User extends MongoObject {
    @Transient
    public static final String SEQUENCE_NAME = "users";
    @Transient
    public static final String COLLECTION_NAME = "users";

    private String id;

    private String name;
}
