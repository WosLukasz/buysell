package com.wosarch.buysell.admin.model.common;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.io.Serializable;

@Data
@MappedSuperclass
public class AdminDatabaseObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version = 0L;

    @UtilityClass
    public static class Fields {
        public static final String OBJECT_ID = "id";
        public static final String VERSION = "version";
    }
}