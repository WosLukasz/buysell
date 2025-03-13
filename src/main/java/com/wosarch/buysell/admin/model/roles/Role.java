package com.wosarch.buysell.admin.model.roles;

import com.wosarch.buysell.admin.model.common.AdminDatabaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Role.ENTITY_NAME)
public class Role extends AdminDatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "roles";

    private String code;

    private List<String> rights;

    @UtilityClass
    public static class Fields {
        public static final String CODE = "code";
        public static final String RIGHTS = "rights";
    }
}
