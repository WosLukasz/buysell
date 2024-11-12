package com.wosarch.buysell.admin.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(User.COLLECTION_NAME)
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users";
    @Transient
    public static final String COLLECTION_NAME = "users";

    @Id
    private String id;

    private String firstName;

    private String name;

    private String email;
}
