package com.wosarch.buysell.admin.model.roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(Role.COLLECTION_NAME)
public class Role {
    @Transient
    public static final String COLLECTION_NAME = "roles";

    @Id
    private String code;

    private List<String> rights;

    @UtilityClass
    public static class Fields {
        public static final String CODE = "code";
        public static final String RIGHTS = "rights";
    }
}
